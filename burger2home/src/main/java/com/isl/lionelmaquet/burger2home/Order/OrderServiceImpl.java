package com.isl.lionelmaquet.burger2home.Order;


import com.isl.lionelmaquet.burger2home.Address.Address;
import com.isl.lionelmaquet.burger2home.Address.AddressService;
import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLineService;
import com.isl.lionelmaquet.burger2home.Keys.KEYS;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLine;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLineService;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserService;
import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.Parcel;
import com.shippo.model.Shipment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BasketService basketService;

    @Autowired
    BasketLineService basketLineService;

    @Autowired
    AddressService addressService;

    @Autowired
    OrderLineService orderLineService;

    @Autowired
    UserService userService;

    static {
        Stripe.apiKey = System.getenv(KEYS.STRIPE_SECRET_KEY.name());
        Shippo.apiKey = System.getenv(KEYS.SHIPPO_SECRET_KEY.name()); // this is the tesk token
    }

    @PersistenceContext
    private EntityManager entityManager;

    private PriceService priceService;

    @Autowired
    public void setPriceService(PriceService priceService){
        this.priceService = priceService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getSingleOrder(Integer orderIdentifier) {
        return orderRepository.findById(orderIdentifier);
    }

    @Override
    public List<Order> getOrdersByUser(Integer userIdentifier) {
        return orderRepository.findByUserId(userIdentifier);
    }

    @Override
    public Order createOrder(Integer basketIdentifier, Integer addressIdentifier) throws StripeException {

        // Retrieve the basket
        Basket basket = basketService.getSingleBasket(basketIdentifier).get();


        // Create a new order with a status "waiting for payment"
        Order order = new Order();
        order.setUserId(basket.getUserId());
        order.setStatus(Order.Status.waiting_for_payment);
        if(basketIdentifier != null) order.setAddressId(addressIdentifier);
        orderRepository.save(order);

        // Create order lines based on basket lines. Adds them to the order
        order.setOrderLines(new HashSet<>(createOrderLinesFromBasketLines(basket.getBasketLines().stream().toList(), order.getId())));

        // Get or create a stripe customer
        User user = userService.getSingleUser(basket.getUserId()).get();
        String currentUserEmail = user.getEmail();
        Map<String, Object> options = new HashMap<>();
        options.put("email", currentUserEmail);
        List<Customer> customers = Customer.list(options).getData();

        Customer customer;
        if(customers.size() > 0){
            customer = customers.get(0);
        } else {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .build();
            customer = Customer.create(params);
        }

        // Create the payment intent to Stripe
        Float totalPrice = getTotalPriceAfterDiscount(order.getOrderLines().stream().toList());
        PaymentIntent paymentIntent = createPaymentIntent(totalPrice, customer.getId());
        order.setPaymentIntent(paymentIntent.getId());

        // Save and return the order
        return orderRepository.save(order);
    }

    private PaymentIntent createPaymentIntent(Float totalPrice, String customerId) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (totalPrice *100))
                        .setCustomer(customerId)
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    private Float getTotalPriceAfterDiscount(List<OrderLine> orderLines) {
        Float totalPrice = 0f;
        for(OrderLine ol : orderLines){
            totalPrice += priceService.getCurrentPriceAfterDiscountByProductId(ol.getProductId()) * ol.getAmount();
        }
        return totalPrice;
    }

    private List<OrderLine> createOrderLinesFromBasketLines(List<BasketLine> basketlines, Integer orderId) {
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        for (BasketLine bl : basketlines){
            OrderLine ol = new OrderLine();
            ol.setOrderId(orderId);
            ol.setProductId(bl.getProductId());
            ol.setAmount(bl.getAmount());
            orderLines.add(orderLineService.createOrderLine(ol));
        }
        return orderLines;
    }

    @Override
    public Order modifyOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderIdentifier) {
        orderRepository.deleteById(orderIdentifier);
    }

    @Override
    public Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException {
        Order order = orderRepository.findById(orderIdentifier).get();

        // Retrieve the payment intent
        String paymentIntentId = order.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        // Attach the customer to the payment method
        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodIdentifier);
        PaymentMethodAttachParams paymentMethodAttachParams = new PaymentMethodAttachParams.Builder().setCustomer(paymentIntent.getCustomer()).build();
        paymentMethod.attach(paymentMethodAttachParams);

        // Confirm the payment intent
        Map<String, Object> paymentIntentConfirmParams = new HashMap<>();
        paymentIntentConfirmParams.put("payment_method", paymentMethodIdentifier);
        paymentIntent.confirm(paymentIntentConfirmParams);


        order.setStatus(Order.Status.confirmed);
        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        Order order = orderRepository.findById(orderIdentifier).get();
        Address address = addressService.getSingleAddress(order.getAddressId()).get();
        User user = userService.getSingleUser(order.getUserId()).get();



        Map<String, Object> addressMap = new HashMap<String, Object>();

        addressMap.put("name", user.getFirstname() + " " + user.getLastname());
        addressMap.put("street1", address.getStreet() + " " + address.getNumber());
        addressMap.put("city", address.getCity());
        addressMap.put("zip", address.getZipcode());
        addressMap.put("country", "BE");
        addressMap.put("email", user.getEmail());

        Map<String, Object> fromAddressMap = new HashMap<String, Object>();

        fromAddressMap.put("name", "Lionel Maquet");
        fromAddressMap.put("street1", "Rue du tige 120");
        fromAddressMap.put("city", "Juprelle");
        fromAddressMap.put("zip", "4450");
        fromAddressMap.put("country", "BE");
        fromAddressMap.put("email", "lionel.maquet@gmail.com");

        com.shippo.model.Address createFromAddress = com.shippo.model.Address.create(fromAddressMap);



        Map<String, Object> parcel = new HashMap<String, Object>();
        parcel.put("length", 10);
        parcel.put("width", 15);
        parcel.put("height", 10);
        parcel.put("distance_unit", "in");
        parcel.put("weight", 1);
        parcel.put("mass_unit", "lb");
        Parcel createParcel = Parcel.create(parcel);

        com.shippo.model.Address createAddress = com.shippo.model.Address.create(addressMap);

        Map<String, Object> shipment = new HashMap<String, Object>();
        shipment.put("address_to", createAddress);
        shipment.put("address_from", createFromAddress);
        shipment.put("parcels", createParcel);

        Shipment createShipment = Shipment.create(shipment);

        return order; // TODO : changer le status




    }
}

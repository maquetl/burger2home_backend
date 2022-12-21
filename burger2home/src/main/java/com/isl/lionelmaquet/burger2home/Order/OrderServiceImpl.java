package com.isl.lionelmaquet.burger2home.Order;


import com.isl.lionelmaquet.burger2home.Address.Address;
import com.isl.lionelmaquet.burger2home.Address.AddressService;
import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLineService;
import com.isl.lionelmaquet.burger2home.CreditCard.CreditCard;
import com.isl.lionelmaquet.burger2home.CreditCard.CreditCardService;
import com.isl.lionelmaquet.burger2home.Keys.KEYS;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLine;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLineService;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserService;
import com.isl.lionelmaquet.burger2home.Utils.StripeUtils;
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
import java.time.Instant;
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

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    private PriceService priceService;

    static {
        Stripe.apiKey = System.getenv(KEYS.STRIPE_SECRET_KEY.name());
        Shippo.apiKey = System.getenv(KEYS.SHIPPO_SECRET_KEY.name()); // this is the tesk token
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
        Order order = createNewOrderFromBasket(basket, addressIdentifier);

        // Create order lines based on basket lines. Adds them to the order
        order.setOrderLines(new HashSet<>(createOrderLinesFromBasketLines(basket.getBasketLines().stream().toList(), order.getId())));

        // Get or create a stripe customer
        Customer customer = StripeUtils.getCustomer(basket.getUserId(), userService);

        // Create the payment intent to Stripe
        Float totalPrice = getTotalPriceAfterDiscount(order.getOrderLines().stream().toList());
        PaymentIntent paymentIntent = createPaymentIntent(totalPrice, customer.getId());
        order.setPaymentIntent(paymentIntent.getId());

        // Save and return the order
        return orderRepository.save(order);
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
        PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntent());

        // Attach the customer to the payment method
        // If the credit card was previously created, the stripe customer already has the payment method attached to it.
        // Attaching it again won't modify anything, so, there's no problem here.
        PaymentMethod paymentMethod = StripeUtils.attachPaymentMethodToCustomer(paymentMethodIdentifier, paymentIntent.getCustomer());

        // Create the payment method in the db
        creditCardService.CreateCreditCardFromStripeCard(order.getUserId(), paymentMethod);

        // Confirm the payment intent
        paymentIntent = StripeUtils.confirmPaymentIntent(paymentIntent, paymentMethodIdentifier);

        // Change the order status and return it
        order.setStatus(Order.Status.payment_confirmed);
        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        Order order = orderRepository.findById(orderIdentifier).get();
        Address address = addressService.getSingleAddress(order.getAddressId()).get();
        User user = userService.getSingleUser(order.getUserId()).get();

        // Create the TO address
        com.shippo.model.Address toAddress = createTOAddress(address, user);

        // Create the FROM address
        com.shippo.model.Address fromAddress = createFROMAddress();

        // Create the parcel
        Parcel createParcel = createParcel();

        // Create the shipment
        createShipment(fromAddress, toAddress, createParcel);

        // Change order status, save and return it
        order.setStatus(Order.Status.shipment_confirmed);
        return orderRepository.save(order);
    }

    private Order createNewOrderFromBasket(Basket basket, Integer addressIdentifier) {
        Order order = new Order();
        order.setUserId(basket.getUserId());
        order.setStatus(Order.Status.waiting_for_payment);
        order.setOrderDate(Instant.now());
        if(addressIdentifier != null) order.setAddressId(addressIdentifier);
        orderRepository.save(order);
        return order;
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

    private void createShipment(com.shippo.model.Address fromAddress, com.shippo.model.Address toAddress, Parcel parcel) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> shipment = new HashMap<String, Object>();
        shipment.put("address_to", toAddress);
        shipment.put("address_from", fromAddress);
        shipment.put("parcels", parcel);
        Shipment createShipment = Shipment.create(shipment);
    }

    private Parcel createParcel() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> parcel = new HashMap<String, Object>();
        parcel.put("length", 10);
        parcel.put("width", 15);
        parcel.put("height", 10);
        parcel.put("distance_unit", "in");
        parcel.put("weight", 1);
        parcel.put("mass_unit", "lb");
        Parcel createParcel = Parcel.create(parcel);
        return createParcel;
    }

    private com.shippo.model.Address createFROMAddress() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        fromAddressMap.put("name", "Lionel Maquet");
        fromAddressMap.put("street1", "Rue du tige 120");
        fromAddressMap.put("city", "Juprelle");
        fromAddressMap.put("zip", "4450");
        fromAddressMap.put("country", "BE");
        fromAddressMap.put("email", "lionel.maquet@gmail.com");
        com.shippo.model.Address createFromAddress = com.shippo.model.Address.create(fromAddressMap);
        return createFromAddress;
    }

    private com.shippo.model.Address createTOAddress(Address address, User user) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("name", user.getFirstname() + " " + user.getLastname());
        addressMap.put("street1", address.getStreet() + " " + address.getNumber());
        addressMap.put("city", address.getCity());
        addressMap.put("zip", address.getZipcode());
        addressMap.put("country", "BE");
        addressMap.put("email", user.getEmail());
        com.shippo.model.Address createAddress = com.shippo.model.Address.create(addressMap);
        return createAddress;
    }
}

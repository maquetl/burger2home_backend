package com.isl.lionelmaquet.burger2home.Order;


import com.isl.lionelmaquet.burger2home.Address.Address;
import com.isl.lionelmaquet.burger2home.Address.AddressService;
import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLineService;
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
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Order createOrder(Integer basketIdentifier) throws StripeException {
        // Step 1 : Get basket
        Basket basket = basketService.getSingleBasket(basketIdentifier).get();

        // Step 2 : Create an order with status waiting for payment
        Order order = new Order();
        order.setUserId(basket.getUserId());
        order.setStatus(Order.Status.waiting_for_payment);
        order = orderRepository.save(order);

        // Step 3 : Create order lines based on basket lines. Add them to the order
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        for (BasketLine bl : basket.getBasketLines()){
            OrderLine ol = new OrderLine();
            ol.setOrderId(order.getId());
            ol.setProductId(bl.getProductId());
            ol.setAmount(bl.getAmount());
            orderLines.add(orderLineService.createOrderLine(ol));
        }

        order = orderRepository.findById(order.getId()).get();

        // Step 5 : Create a payment intent to stripe

        Float totalPrice = 0f;
        for(OrderLine ol : orderLines){
            totalPrice += priceService.getCurrentPriceAfterDiscountByProductId(ol.getProductId()) * ol.getAmount();
        }

        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (totalPrice*100))
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        order.setPaymentIntent(paymentIntent.getId());

        // Step 7 : Return the order
        return orderRepository.save(order);

    }

    @Override
    public void modifyOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderIdentifier) {
        orderRepository.deleteById(orderIdentifier);
    }

    @Override
    public Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException {
        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        Order order = orderRepository.findById(orderIdentifier).get();

        String paymentIntentId = order.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodIdentifier);

        paymentIntent.confirm(params);

        order.setStatus(Order.Status.confirmed);

        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        Order order = orderRepository.findById(orderIdentifier).get();
        Address address = addressService.getSingleAddress(order.getAddressId()).get();
        User user = userService.getSingleUser(order.getUserId()).get();


        Shippo.apiKey = "shippo_test_1e979141c9601219f667b3a1e19f58cda1a285b5"; // this is the tesk token
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

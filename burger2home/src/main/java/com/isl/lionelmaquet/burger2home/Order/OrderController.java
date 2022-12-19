package com.isl.lionelmaquet.burger2home.Order;

import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    /**
     * Gets an order based on the id
     * @param orderIdentifier {@link Integer} the order identifier to use
     * @return {@link Optional}<{@link Order}>
     */
    @GetMapping("/orders/{orderIdentifier}")
    Optional<Order> getSingleOrder(@PathVariable Integer orderIdentifier){
        return orderService.getSingleOrder(orderIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/orders")
    List<Order> getOrdersByUser(@PathVariable Integer userIdentifier){
        return orderService.getOrdersByUser(userIdentifier);
    }


    @GetMapping("/create-order")
    Order createSingleOrder(@RequestParam Integer basketIdentifier) throws StripeException {
        return orderService.createOrder(basketIdentifier);
    }

    @GetMapping("/confirm-order")
    Order confirmOrder(@RequestParam Integer orderIdentifier, @RequestParam String paymentMethodIdentifier) throws StripeException {
        return orderService.confirmOrder(orderIdentifier, paymentMethodIdentifier);
    }

    @GetMapping("/ship")
    Order shipOrder(@RequestParam Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        return orderService.shipOrder(orderIdentifier);
    }

    @PutMapping("/orders")
    void modifySingleOrder(@RequestBody Order order){
        orderService.modifyOrder(order);
    }

    @DeleteMapping("/orders/{orderIdentifier}")
    void deleteSingleOrder(@PathVariable Integer orderIdentifier){
        orderService.deleteOrder(orderIdentifier);
    }

    @PostMapping("/orders/intents")
    void createOrderIntent(@RequestParam(required = true) Integer basketId) {

    }



    @GetMapping("/orders/stripe/create-payment-intent")
    String createPaymentIntent() throws StripeException {
        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(1099L)
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        System.out.println(paymentIntent);
        return paymentIntent.getId();


    }
    
    @GetMapping("/orders/stripe/create-payment-method")
    String createPaymentMethod() throws StripeException {
        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        PaymentMethodCreateParams params = PaymentMethodCreateParams.builder()
                .setType(PaymentMethodCreateParams.Type.CARD)
                .setCard(
                        PaymentMethodCreateParams.CardDetails.builder()
                                .setCvc("444")
                                .setExpMonth(12L)
                                .setExpYear(2024L)
                                .setNumber("4242 4242 4242 4242")
                                .build()
                ).build();
        PaymentMethod paymentMethod = PaymentMethod.create(params);
        System.out.println(paymentMethod);
        return paymentMethod.getId();
    }

    @GetMapping("/orders/stripe/confirm-payment")
    String confirmPayment(@RequestParam String paymentIntentId, @RequestParam String paymentMethodId) throws StripeException {
        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        PaymentIntentConfirmParams paymentIntentConfirmParams = PaymentIntentConfirmParams.builder().setPaymentMethod(paymentMethodId).build();
        paymentIntent = paymentIntent.confirm(paymentIntentConfirmParams);
        return paymentIntent.getStatus();
    }

}

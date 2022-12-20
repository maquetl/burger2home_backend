package com.isl.lionelmaquet.burger2home.Order;

import com.isl.lionelmaquet.burger2home.Keys.KEYS;
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


    @GetMapping("/orders/create-order")
    Order createSingleOrder(@RequestParam Integer basketIdentifier, @RequestParam(required = false) Integer addressIdentifier) throws StripeException {
        return orderService.createOrder(basketIdentifier, addressIdentifier);
    }

    @GetMapping("/orders/confirm-order")
    Order confirmOrder(@RequestParam Integer orderIdentifier, @RequestParam String paymentMethodIdentifier) throws StripeException {
        return orderService.confirmOrder(orderIdentifier, paymentMethodIdentifier);
    }

    @GetMapping("/orders/confirm-delivery")
    Order shipOrder(@RequestParam Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        return orderService.shipOrder(orderIdentifier);
    }

    @PutMapping("/orders")
    Order modifySingleOrder(@RequestBody Order order){
        return orderService.modifyOrder(order);
    }

    @DeleteMapping("/orders/{orderIdentifier}")
    void deleteSingleOrder(@PathVariable Integer orderIdentifier){
        orderService.deleteOrder(orderIdentifier);
    }


    @GetMapping("/orders/stripe/create-payment-method")
    String createPaymentMethod() throws StripeException {
        Stripe.apiKey = System.getenv(KEYS.STRIPE_SECRET_KEY.name());
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

}

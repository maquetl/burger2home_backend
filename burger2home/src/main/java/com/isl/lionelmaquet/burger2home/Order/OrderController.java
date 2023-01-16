package com.isl.lionelmaquet.burger2home.Order;

import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.Keys.KEYS;
import com.isl.lionelmaquet.burger2home.Utils.AuthUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    BasketService basketService;

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    List<Order> getAllOrders() throws StripeException {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderIdentifier}")
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("returnObject != null ? returnObject.userId == authentication.principal.id or hasRole('ADMIN') : hasRole('ADMIN')")
    Order getSingleOrder(@PathVariable Integer orderIdentifier) {
        Optional<Order> order = orderService.getSingleOrder(orderIdentifier);
        return order.orElse(null);
    }

    @GetMapping("/users/{userIdentifier}/orders")
    @PreAuthorize("hasRole('ADMIN') or #userIdentifier == authentication.principal.id")
    List<Order> getOrdersByUser(@PathVariable Integer userIdentifier) {
        return orderService.getOrdersByUser(userIdentifier);
    }

    @GetMapping("/orders/create-order")
    @PreAuthorize("isAuthenticated()")
    Order createSingleOrder(@RequestParam Integer basketIdentifier, @RequestParam(required = false) Integer addressIdentifier) throws StripeException {

        basketService.getSingleBasket(basketIdentifier).ifPresent(basket -> {
            if (!Objects.equals(Long.valueOf(basket.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });


        return orderService.createOrder(basketIdentifier, addressIdentifier);
    }

    @GetMapping("/orders/confirm-order")
    @PreAuthorize("isAuthenticated()")
    Order confirmOrder(@RequestParam Integer orderIdentifier, @RequestParam String paymentMethodIdentifier) throws StripeException {

        orderService.getSingleOrder(orderIdentifier).ifPresent(order -> {
            if (!Objects.equals(Long.valueOf(order.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderService.confirmOrder(orderIdentifier, paymentMethodIdentifier);
    }

    @GetMapping("/orders/confirm-delivery")
    @PreAuthorize("isAuthenticated()")
    Order shipOrder(@RequestParam Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {

        orderService.getSingleOrder(orderIdentifier).ifPresent(order -> {
            if (!Objects.equals(Long.valueOf(order.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderService.shipOrder(orderIdentifier);
    }

    @PutMapping("/orders")
    @PreAuthorize("#order.userId == authentication.principal.id or hasRole('ADMIN')")
    Order modifySingleOrder(@RequestBody Order order){
        return orderService.modifyOrder(order);
    }

    // Test method for dev purposes
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

    @GetMapping("/orders/{orderIdentifier}/secret")
    @PreAuthorize("isAuthenticated()")
    String getClientSecret(@PathVariable Integer orderIdentifier){

        orderService.getSingleOrder(orderIdentifier).ifPresent(order -> {
            if (!Objects.equals(Long.valueOf(order.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderService.getClientSecret(orderIdentifier);
    }

}

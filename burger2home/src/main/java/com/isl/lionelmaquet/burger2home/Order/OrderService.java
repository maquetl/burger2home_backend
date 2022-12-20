package com.isl.lionelmaquet.burger2home.Order;

import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.stripe.exception.StripeException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getSingleOrder(Integer orderIdentifier);

    List<Order> getOrdersByUser(Integer userIdentifier);

    Order createOrder(Integer basketIdentifier, Integer addressIdentifier) throws StripeException;

    Order modifyOrder(Order order);

    void deleteOrder(Integer orderIdentifier);

    Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException;

    Order shipOrder(Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException;
}

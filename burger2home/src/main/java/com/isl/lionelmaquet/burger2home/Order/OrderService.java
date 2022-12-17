package com.isl.lionelmaquet.burger2home.Order;

import com.stripe.exception.StripeException;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getSingleOrder(Integer orderIdentifier);

    List<Order> getOrdersByUser(Integer userIdentifier);

    Order createOrder(Integer basketIdentifier) throws StripeException;

    void modifyOrder(Order order);

    void deleteOrder(Integer orderIdentifier);

    Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException;
}

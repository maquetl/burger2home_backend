package com.isl.lionelmaquet.burger2home.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();

    Optional<Order> getSingleOrder(Integer orderIdentifier);

    List<Order> getOrdersByUser(Integer userIdentifier);

    void createOrder(Order order);

    void modifyOrder(Order order);

    void deleteOrder(Integer orderIdentifier);
}

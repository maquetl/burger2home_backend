package com.isl.lionelmaquet.burger2home.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository rep;

    @Override
    public List<Order> getAllOrders() {
        return rep.findAll();
    }

    @Override
    public Optional<Order> getSingleOrder(Integer orderIdentifier) {
        return rep.findById(orderIdentifier);
    }

    @Override
    public List<Order> getOrdersByUser(Integer userIdentifier) {
        return rep.findByUserId(userIdentifier);
    }

    @Override
    public void createOrder(Order order) {
        rep.save(order);
    }

    @Override
    public void modifyOrder(Order order) {
        rep.save(order);
    }

    @Override
    public void deleteOrder(Integer orderIdentifier) {
        rep.deleteById(orderIdentifier);
    }
}

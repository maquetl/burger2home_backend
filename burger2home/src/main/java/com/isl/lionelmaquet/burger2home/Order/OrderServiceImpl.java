package com.isl.lionelmaquet.burger2home.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

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
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void modifyOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderIdentifier) {
        orderRepository.deleteById(orderIdentifier);
    }
}

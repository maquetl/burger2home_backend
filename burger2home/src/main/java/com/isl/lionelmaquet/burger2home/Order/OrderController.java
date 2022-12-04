package com.isl.lionelmaquet.burger2home.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderIdentifier}")
    Optional<Order> getSingleOrder(@PathVariable Integer orderIdentifier){
        return orderService.getSingleOrder(orderIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/orders")
    List<Order> getOrdersByUser(@PathVariable Integer userIdentifier){
        return orderService.getOrdersByUser(userIdentifier);
    }

    @PostMapping("/orders")
    void createSingleOrder(@RequestBody Order order){
        orderService.createOrder(order);
    }

    @PutMapping("/orders")
    void modifySingleOrder(@RequestBody Order order){
        orderService.modifyOrder(order);
    }

    @DeleteMapping("/orders/{orderIdentifier}")
    void deleteSingleOrder(@PathVariable Integer orderIdentifier){
        orderService.deleteOrder(orderIdentifier);
    }

}

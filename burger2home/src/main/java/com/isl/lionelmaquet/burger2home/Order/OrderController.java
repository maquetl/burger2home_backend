package com.isl.lionelmaquet.burger2home.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    OrderService serv;

    @GetMapping("/orders")
    List<Order> getAllOrders(){
        return serv.getAllOrders();
    }

    @GetMapping("/orders/{orderIdentifier}")
    Optional<Order> getSingleOrder(@PathVariable Integer orderIdentifier){
        return serv.getSingleOrder(orderIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/orders")
    List<Order> getOrdersByUser(@PathVariable Integer userIdentifier){
        return serv.getOrdersByUser(userIdentifier);
    }

    @PostMapping("/orders")
    void createSingleOrder(@RequestBody Order order){
        serv.createOrder(order);
    }

    @PutMapping("/orders")
    void modifySingleOrder(@RequestBody Order order){
        serv.modifyOrder(order);
    }

    @DeleteMapping("/orders/{orderIdentifier}")
    void deleteSingleOrder(@PathVariable Integer orderIdentifier){
        serv.deleteOrder(orderIdentifier);
    }

}

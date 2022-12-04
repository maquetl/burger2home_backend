package com.isl.lionelmaquet.burger2home.OrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderLineController {

    @Autowired
    OrderLineService orderLineService;

    @GetMapping("/orderLines")
    List<OrderLine> getAllOrderLines(){
        return orderLineService.getAllOrderLines();
    }

    @GetMapping("/orderLines/{orderLineIdentifier}")
    Optional<OrderLine> getSingleOrderLine(@PathVariable Integer orderLineIdentifier){
        return orderLineService.getSingleOrderLine(orderLineIdentifier);
    }

    @GetMapping("/orders/{orderIdentifier}/orderLines")
    List<OrderLine> getOrderLinesByOrder(@PathVariable Integer orderIdentifier){
        return orderLineService.getOrderLinesByOrder(orderIdentifier);
    }

    @PostMapping("/orderLines")
    void createOrderLine(@RequestBody OrderLine orderLine){
        orderLineService.createOrderLine(orderLine);
    }

    @PutMapping("/orderLines")
    void modifyOrderLine(@RequestBody OrderLine orderLine){
        orderLineService.modifyOrderLine(orderLine);
    }

    @DeleteMapping("/orderLines/{orderLineIdentifier}")
    void deleteOrderLine(@PathVariable Integer orderLineIdentifier){
        orderLineService.deleteOrderLine(orderLineIdentifier);
    }
}

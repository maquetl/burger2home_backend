package com.isl.lionelmaquet.burger2home.OrderLine;

import com.isl.lionelmaquet.burger2home.Order.OrderService;
import com.isl.lionelmaquet.burger2home.Utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class OrderLineController {

    @Autowired
    OrderLineService orderLineService;

    @Autowired
    OrderService orderService;

    @GetMapping("/orderLines")
    @PreAuthorize("hasRole('ADMIN')")
    List<OrderLine> getAllOrderLines(){
        return orderLineService.getAllOrderLines();
    }

    @GetMapping("/orderLines/{orderLineIdentifier}")
    @PreAuthorize("isAuthenticated()")
    Optional<OrderLine> getSingleOrderLine(@PathVariable Integer orderLineIdentifier){

        Optional<OrderLine> orderLine = orderLineService.getSingleOrderLine(orderLineIdentifier);

        orderLine.ifPresent(ol -> {
            orderService.getSingleOrder(ol.getOrderId()).ifPresent(o -> {
                if (!Objects.equals(Long.valueOf(o.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
            });
        });

        return orderLine;
    }

    @GetMapping("/orders/{orderIdentifier}/orderLines")
    @PreAuthorize("isAuthenticated()")
    List<OrderLine> getOrderLinesByOrder(@PathVariable Integer orderIdentifier){

        orderService.getSingleOrder(orderIdentifier).ifPresent(o -> {
            if (!Objects.equals(Long.valueOf(o.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderLineService.getOrderLinesByOrder(orderIdentifier);
    }

    @PostMapping("/orderLines")
    @PreAuthorize("isAuthenticated()")
    OrderLine createOrderLine(@RequestBody OrderLine orderLine){

        orderService.getSingleOrder(orderLine.getOrderId()).ifPresent(o -> {
            if (!Objects.equals(Long.valueOf(o.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderLineService.createOrderLine(orderLine);
    }

    @PutMapping("/orderLines")
    @PreAuthorize("isAuthenticated()")
    OrderLine modifyOrderLine(@RequestBody OrderLine orderLine){

        orderService.getSingleOrder(orderLine.getOrderId()).ifPresent(o -> {
            if (!Objects.equals(Long.valueOf(o.getUserId()), AuthUtils.getCurrentUserId()) && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return orderLineService.modifyOrderLine(orderLine);
    }
}

package com.isl.lionelmaquet.burger2home.OrderLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    OrderLineRepository orderLineRepository;

    @Override
    public List<OrderLine> getAllOrderLines() {
        return orderLineRepository.findAll();
    }

    @Override
    public Optional<OrderLine> getSingleOrderLine(Integer orderLineIdentifier) {
        return orderLineRepository.findById(orderLineIdentifier);
    }

    @Override
    public List<OrderLine> getOrderLinesByOrder(Integer orderIdentifier) {
        return orderLineRepository.findByOrder(orderIdentifier);
    }

    @Override
    public void createOrderLine(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }

    @Override
    public void modifyOrderLine(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }

    @Override
    public void deleteOrderLine(Integer orderLineIdentifier) {
        orderLineRepository.deleteById(orderLineIdentifier);
    }
}

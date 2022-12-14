package com.isl.lionelmaquet.burger2home.OrderLine;

import java.util.List;
import java.util.Optional;

public interface OrderLineService {
    List<OrderLine> getAllOrderLines();

    Optional<OrderLine> getSingleOrderLine(Integer orderLineIdentifier);

    List<OrderLine> getOrderLinesByOrder(Integer orderIdentifier);

    OrderLine createOrderLine(OrderLine orderLine);

    OrderLine modifyOrderLine(OrderLine orderLine);
}

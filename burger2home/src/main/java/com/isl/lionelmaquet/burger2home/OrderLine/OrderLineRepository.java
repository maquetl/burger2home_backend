package com.isl.lionelmaquet.burger2home.OrderLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    List<OrderLine> findByOrderId(Integer orderIdentifier);
}
package com.isl.lionelmaquet.burger2home.BasketLine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketLineRepository extends JpaRepository<BasketLine, Integer> {
    List<BasketLine> findByBasketId(Long basketIdentifier);
}
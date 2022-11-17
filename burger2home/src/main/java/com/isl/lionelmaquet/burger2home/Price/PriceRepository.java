package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    @Query("SELECT p FROM Price p WHERE p.product.id = ?1")
    List<Price> findPricesByProductId(Integer productId);

    @Query("SELECT p FROM Price p WHERE p.product.id = ?1 AND current_timestamp BETWEEN p.startDate AND p.endDate")
    Price findCurrentPriceByProductId(Integer productId);
}
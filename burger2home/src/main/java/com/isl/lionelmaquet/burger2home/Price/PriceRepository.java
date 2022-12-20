package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

    List<Price> findByProductId(Integer productId);

    @Query("SELECT p FROM Price p WHERE p.productId = ?1 AND current_timestamp BETWEEN p.startDate AND p.endDate")
    Price findCurrentPriceByProductId(Integer productId);

//    @Query("SELECT p FROM Price p WHERE p.productId = ?2 AND p.startDate = (SELECT MAX(p2.startDate) FROM Price p2 WHERE p2.startDate < ?1 AND p2.endDate > ?1)")
//    Optional<Price> findPreviousPrice(LocalDate startDate, Integer productId);

    @Query("SELECT p FROM Price p WHERE p.productId = ?1 AND p.startDate > current_timestamp")
    Optional<Price> findNextPrice(Integer productId);
}
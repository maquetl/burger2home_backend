package com.isl.lionelmaquet.burger2home.StockHistorization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistorizationRepository extends JpaRepository<StockHistorization, Integer> {
}
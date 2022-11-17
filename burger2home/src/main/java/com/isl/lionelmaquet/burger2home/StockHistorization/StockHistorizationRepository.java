package com.isl.lionelmaquet.burger2home.StockHistorization;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistorizationRepository extends JpaRepository<StockHistorization, Integer> {

    // Get current stock
    List<StockHistorization> findTopByIngredientIdOrderByCreationDateDesc(Integer ingredientId);
}
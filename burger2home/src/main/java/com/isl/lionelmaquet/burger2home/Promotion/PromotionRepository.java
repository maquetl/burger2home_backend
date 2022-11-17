package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("SELECT promo FROM Promotion promo WHERE ?1 MEMBER OF promo.products AND current_timestamp BETWEEN promo.startDate AND promo.endDate")
    Optional<Promotion> findCurrentPromotion(Product product);
}
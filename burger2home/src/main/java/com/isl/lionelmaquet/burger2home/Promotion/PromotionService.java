package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;

import java.util.Optional;

public interface PromotionService {
    Optional<Promotion> getCurrentPromotion(Product product);
}

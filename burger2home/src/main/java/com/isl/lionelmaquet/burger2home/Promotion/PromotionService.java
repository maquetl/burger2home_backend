package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Optional<Promotion> getCurrentPromotion(Product product);

    Optional<Promotion> getCurrentPromotion(Integer productId);

    List<Promotion> getAllPromotions();

    Optional<Promotion> getSinglePromotion(Integer promotionIdentifier);

    void createSinglePromotion(Promotion promotion);

    void modifySinglePromotion(Promotion promotion);

    void deleteSinglePromotion(Integer promotionIdentifier);
}

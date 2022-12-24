package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.Promotion.Translation.PromotionTranslation;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    Optional<Promotion> getCurrentPromotion(Product product);

    Optional<Promotion> getCurrentPromotion(Integer productId);

    List<Promotion> getAllPromotions();

    Optional<Promotion> getSinglePromotion(Integer promotionIdentifier);

    Promotion createSinglePromotion(Promotion promotion);

    Promotion modifySinglePromotion(Promotion promotion);
}

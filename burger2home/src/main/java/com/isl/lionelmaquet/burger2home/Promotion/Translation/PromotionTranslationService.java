package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import java.util.List;
import java.util.Optional;

public interface PromotionTranslationService {
    List<PromotionTranslation> getAllPromotionTranslations();

    Optional<PromotionTranslation> getSinglePromotionTranslation(Integer promotionTranslationIdentifier);

    List<PromotionTranslation> getPromotionTranslationsByPromotion(Integer promotionIdentifier);

    void createSinglePromotionTranslation(PromotionTranslation promotionTranslation);

    void modifySinglePromotionTranslation(PromotionTranslation promotionTranslation);

    void deleteSinglePromotionTranslation(Integer promotionTranslationIdentifier);
}

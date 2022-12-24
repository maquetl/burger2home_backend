package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import java.util.List;
import java.util.Optional;

public interface PromotionTranslationService {
    List<PromotionTranslation> getAllPromotionTranslations();

    List<PromotionTranslation> getByLanguage(Integer languageId);

    Optional<PromotionTranslation> getSinglePromotionTranslation(Integer promotionTranslationIdentifier);

    List<PromotionTranslation> getPromotionTranslationsByPromotion(Integer promotionIdentifier);

    PromotionTranslation createSinglePromotionTranslation(PromotionTranslation promotionTranslation);

    PromotionTranslation modifySinglePromotionTranslation(PromotionTranslation promotionTranslation);

    void deleteSinglePromotionTranslation(Integer promotionTranslationIdentifier);
}

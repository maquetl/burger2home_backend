package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionTranslationServiceImpl implements PromotionTranslationService {

    @Autowired
    PromotionTranslationRepository promotionTranslationRepository;

    @Override
    public List<PromotionTranslation> getAllPromotionTranslations() {
        return promotionTranslationRepository.findAll();
    }

    @Override
    public Optional<PromotionTranslation> getSinglePromotionTranslation(Integer promotionTranslationIdentifier) {
        return promotionTranslationRepository.findById(promotionTranslationIdentifier);
    }

    @Override
    public List<PromotionTranslation> getPromotionTranslationsByPromotion(Integer promotionIdentifier) {
        return promotionTranslationRepository.findByPromotionId(promotionIdentifier);
    }

    @Override
    public PromotionTranslation createSinglePromotionTranslation(PromotionTranslation promotionTranslation) {
        return promotionTranslationRepository.save(promotionTranslation);
    }

    @Override
    public PromotionTranslation modifySinglePromotionTranslation(PromotionTranslation promotionTranslation) {
        return promotionTranslationRepository.save(promotionTranslation);
    }

    @Override
    public void deleteSinglePromotionTranslation(Integer promotionTranslationIdentifier) {
        promotionTranslationRepository.deleteById(promotionTranslationIdentifier);
    }
}

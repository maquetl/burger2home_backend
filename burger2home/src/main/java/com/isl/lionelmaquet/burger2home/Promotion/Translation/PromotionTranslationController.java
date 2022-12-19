package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslation;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PromotionTranslationController {

    @Autowired
    PromotionTranslationService promotionTranslationService;

    @GetMapping("/promotions/translations")
    List<PromotionTranslation> getAllPromotionTranslations(@RequestParam(required = false) String language){
        return (List<PromotionTranslation>) TranslationUtils.filterTranslationsByLanguage(promotionTranslationService.getAllPromotionTranslations(), language);
    }

    @GetMapping("/promotions/translations/{promotionTranslationIdentifier}")
    Optional<PromotionTranslation> getSinglePromotionTranslation(@PathVariable Integer promotionTranslationIdentifier){
        return promotionTranslationService.getSinglePromotionTranslation(promotionTranslationIdentifier);
    }

    @GetMapping("/promotions/{promotionIdentifier}/translations")
    List<PromotionTranslation> getPromotionTranslationsByPromotion(@PathVariable Integer promotionIdentifier, @RequestParam(required = false) String language){
        return (List<PromotionTranslation>) TranslationUtils.filterTranslationsByLanguage(promotionTranslationService.getPromotionTranslationsByPromotion(promotionIdentifier), language);
    }

    @PostMapping("/promotions/translations")
    void createSinglePromotionTranslation(@RequestBody PromotionTranslation promotionTranslation){
        promotionTranslationService.createSinglePromotionTranslation(promotionTranslation);
    }

    @PutMapping("/promotions/translations")
    void modifySingelPromotionTranslation(@RequestBody PromotionTranslation promotionTranslation){
        promotionTranslationService.modifySinglePromotionTranslation(promotionTranslation);
    }

    @DeleteMapping("/promotions/translations/{promotionTranslationIdentifier}")
    void deleteSinglePromotionTranslation(@PathVariable Integer promotionTranslationIdentifier){
        promotionTranslationService.deleteSinglePromotionTranslation(promotionTranslationIdentifier);
    }
}

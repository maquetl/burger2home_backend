package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslation;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MARKETING')")
    PromotionTranslation createSinglePromotionTranslation(@RequestBody PromotionTranslation promotionTranslation){
        return promotionTranslationService.createSinglePromotionTranslation(promotionTranslation);
    }

    @PutMapping("/promotions/translations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MARKETING')")
    PromotionTranslation modifySingelPromotionTranslation(@RequestBody PromotionTranslation promotionTranslation){
        return promotionTranslationService.modifySinglePromotionTranslation(promotionTranslation);
    }

    @DeleteMapping("/promotions/translations/{promotionTranslationIdentifier}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MARKETING')")
    void deleteSinglePromotionTranslation(@PathVariable Integer promotionTranslationIdentifier){
        promotionTranslationService.deleteSinglePromotionTranslation(promotionTranslationIdentifier);
    }
}

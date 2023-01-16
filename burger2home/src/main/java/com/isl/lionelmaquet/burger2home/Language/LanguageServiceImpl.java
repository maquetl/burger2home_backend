package com.isl.lionelmaquet.burger2home.Language;

import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslationService;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslationService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationService;
import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslation;
import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslationService;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import com.isl.lionelmaquet.burger2home.Promotion.Translation.PromotionTranslation;
import com.isl.lionelmaquet.burger2home.Promotion.Translation.PromotionTranslationService;
import com.isl.lionelmaquet.burger2home.Type.Translation.TypeTranslation;
import com.isl.lionelmaquet.burger2home.Type.Translation.TypeTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    AllergenTranslationService allergenTranslationService;

    @Autowired
    IngredientTranslationService ingredientTranslationService;

    @Autowired
    TypeTranslationService typeTranslationService;

    @Autowired
    ProductTranslationService productTranslationService;

    @Autowired
    ProductFamilyTranslationService productFamilyTranslationService;

    @Autowired
    PromotionTranslationService promotionTranslationService;

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> getSingleLanguage(Integer languageIdentifier) {
        return languageRepository.findById(languageIdentifier);
    }

    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language modifyLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(Integer languageIdentifier) {
        for (AllergenTranslation at : allergenTranslationService.getByLanguage(languageIdentifier)){
            allergenTranslationService.deleteAllergenTranslation(at.getId());
        }
        for (IngredientTranslation it : ingredientTranslationService.findByLanguage(languageIdentifier)){
            ingredientTranslationService.deleteIngredientTranslation(it.getId());
        }
        for (ProductTranslation pt : productTranslationService.findByLanguage(languageIdentifier)){
            productTranslationService.deleteById(pt.getId());
        }
        for (ProductFamilyTranslation pft : productFamilyTranslationService.findByLanguage(languageIdentifier)){
            productFamilyTranslationService.deleteSingleProductFamilyTranslation(pft.getId());
        }
        for (PromotionTranslation pt : promotionTranslationService.getByLanguage(languageIdentifier)){
            promotionTranslationService.deleteSinglePromotionTranslation(pt.getId());
        }

        for (TypeTranslation tt : typeTranslationService.getByLanguage(languageIdentifier)){
            typeTranslationService.deleteById(tt.getId());
        }

        languageRepository.deleteById(languageIdentifier);
    }
}

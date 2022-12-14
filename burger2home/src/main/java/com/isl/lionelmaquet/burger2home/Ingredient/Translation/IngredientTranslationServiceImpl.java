package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientTranslationServiceImpl implements IngredientTranslationService {

    @Autowired
    IngredientTranslationRepository ingredientTranslationRepository;

    @Override
    public List<IngredientTranslation> findByIngredient(Integer ingredientIdentifier) {
        return ingredientTranslationRepository.findByIngredientId(ingredientIdentifier);
    }

    @Override
    public List<IngredientTranslation> findByLanguage(Integer languageIdentifier) {
        return ingredientTranslationRepository.findByLanguageId(languageIdentifier);
    }

    @Override
    public Optional<IngredientTranslation> findIngredientTranslation(Integer ingredientTranslationIdentifier) {
        return ingredientTranslationRepository.findById(ingredientTranslationIdentifier);
    }

    @Override
    public IngredientTranslation createIngredientTranslation(IngredientTranslation ingredientTranslation) {
        return ingredientTranslationRepository.save(ingredientTranslation);
    }

    @Override
    public IngredientTranslation modifyIngredientTranslation(IngredientTranslation ingredientTranslation) {
        return ingredientTranslationRepository.save(ingredientTranslation);
    }

    @Override
    public void deleteIngredientTranslation(Integer ingredientTranslationIdentifier) {
        ingredientTranslationRepository.deleteById(ingredientTranslationIdentifier);
    }

    @Override
    public List<IngredientTranslation> getAllIngredientTranslations() {
        return ingredientTranslationRepository.findAll();
    }
}

package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import java.util.List;
import java.util.Optional;

public interface IngredientTranslationService {
    List<IngredientTranslation> findByIngredient(Integer ingredientIdentifier);

    List<IngredientTranslation> findByLanguage(Integer languageIdentifier);

    Optional<IngredientTranslation> findIngredientTranslation(Integer ingredientTranslationIdentifier);

    IngredientTranslation createIngredientTranslation(IngredientTranslation ingredientTranslation);

    IngredientTranslation modifyIngredientTranslation(IngredientTranslation ingredientTranslation);

    void deleteIngredientTranslation(Integer ingredientTranslationIdentifier);

    List<IngredientTranslation> getAllIngredientTranslations();
}

package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import java.util.List;
import java.util.Optional;

public interface IngredientTranslationService {
    List<IngredientTranslation> findByIngredient(Integer ingredientIdentifier);

    Optional<IngredientTranslation> findIngredientTranslation(Integer ingredientTranslationIdentifier);

    void createIngredientTranslation(IngredientTranslation ingredientTranslation);

    void modifyIngredientTranslation(IngredientTranslation ingredientTranslation);

    void deleteIngredientTranslation(Integer ingredientTranslationIdentifier);

    List<IngredientTranslation> getAllIngredientTranslations();
}

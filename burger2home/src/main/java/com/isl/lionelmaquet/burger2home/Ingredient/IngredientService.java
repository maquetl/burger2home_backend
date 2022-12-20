package com.isl.lionelmaquet.burger2home.Ingredient;

import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Optional<Ingredient> getSingleIngredient(Integer ingredientIdentifier);

    List<Ingredient> getIngredientsByProduct(Integer productIdentifier);

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient modifyIngredient(Ingredient ingredient);

    void deleteIngredient(Integer ingredientIdentifier);
}

package com.isl.lionelmaquet.burger2home.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> getAllIngredients();

    Optional<Ingredient> getSingleIngredient(Integer ingredientIdentifier);

    List<Ingredient> getIngredientsByProduct(Integer productIdentifier);

    void createIngredient(Ingredient ingredient);

    void modifyIngredient(Ingredient ingredient);

    void deleteIngredient(Integer ingredientIdentifier);
}

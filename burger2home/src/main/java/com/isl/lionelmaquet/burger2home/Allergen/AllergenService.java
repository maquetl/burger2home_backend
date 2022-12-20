package com.isl.lionelmaquet.burger2home.Allergen;

import java.util.List;
import java.util.Optional;

public interface AllergenService {
    List<Allergen> getAllAllergens();

    List<Allergen> getAllergensByIngredient(Integer ingredientIdentifier);

    Optional<Allergen> getSingleAllergen(Integer allergenIdentifier);

    Allergen createAllergen(Allergen allergen);

    void deleteAllergen(Integer allergenIdentifier);

    Allergen modifyAllergen(Allergen allergen);
}

package com.isl.lionelmaquet.burger2home.Allergen;

import java.util.List;
import java.util.Optional;

public interface AllergenService {
    List<Allergen> getAllAllergens();

    List<Allergen> getAllergensByIngredient(Integer ingredientIdentifier);

    Optional<Allergen> getSingleAllergen(Integer allergenIdentifier);

    void createAllergen(Allergen allergen);

    void deleteAllergen(Integer allergenIdentifier);

    void modifyAllergen(Allergen allergen);
}

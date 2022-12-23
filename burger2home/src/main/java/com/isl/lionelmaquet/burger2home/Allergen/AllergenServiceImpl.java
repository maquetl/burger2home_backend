package com.isl.lionelmaquet.burger2home.Allergen;

import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslationService;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergenServiceImpl implements AllergenService {

    @Autowired
    AllergenRepository allergenRepository;

    @Autowired
    AllergenTranslationService allergenTranslationService;

    @Autowired
    IngredientService ingredientService;

    @Override
    public List<Allergen> getAllAllergens() {
        return allergenRepository.findAll();
    }

    @Override
    public List<Allergen> getAllergensByIngredient(Integer ingredientIdentifier) {
        return allergenRepository.findByIngredientsId(ingredientIdentifier);
    }

    @Override
    public Optional<Allergen> getSingleAllergen(Integer allergenIdentifier) {
        return allergenRepository.findById(allergenIdentifier);
    }

    @Override
    public Allergen createAllergen(Allergen allergen) {
        return allergenRepository.save(allergen);
    }

    @Override
    public void deleteAllergen(Integer allergenIdentifier) {
        Allergen allergen = allergenRepository.findById(allergenIdentifier).get();
        for (AllergenTranslation at : allergen.getAllergenTranslations()){
            allergenTranslationService.deleteAllergenTranslation(at.getId());
        }
        for (Ingredient ingredient : allergen.getIngredients()){
            ingredient.getAllergens().remove(allergen);
        }
        allergenRepository.deleteById(allergenIdentifier);
    }

    @Override
    public Allergen modifyAllergen(Allergen allergen) {
        return allergenRepository.save(allergen);
    }
}

package com.isl.lionelmaquet.burger2home.Allergen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergenServiceImpl implements AllergenService {

    @Autowired
    AllergenRepository allergenRepository;

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
    public void createAllergen(Allergen allergen) {
        allergenRepository.save(allergen);
    }

    @Override
    public void deleteAllergen(Integer allergenIdentifier) {
        allergenRepository.deleteById(allergenIdentifier);
    }

    @Override
    public void modifyAllergen(Allergen allergen) {
        allergenRepository.save(allergen);
    }
}

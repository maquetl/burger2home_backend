package com.isl.lionelmaquet.burger2home.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Optional<Ingredient> getSingleIngredient(Integer ingredientIdentifier) {
        return ingredientRepository.findById(ingredientIdentifier);
    }

    @Override
    public List<Ingredient> getIngredientsByProduct(Integer productIdentifier) {
        return ingredientRepository.findByProductsId(productIdentifier);
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient modifyIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredient(Integer ingredientIdentifier) {
        ingredientRepository.deleteById(ingredientIdentifier);
    }
}

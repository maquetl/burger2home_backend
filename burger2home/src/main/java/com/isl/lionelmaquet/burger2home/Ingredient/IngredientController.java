package com.isl.lionelmaquet.burger2home.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @GetMapping("/ingredients")
    List<Ingredient> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/ingredients/{ingredientIdentifier}")
    Optional<Ingredient> getSingleIngredient(@PathVariable Integer ingredientIdentifier){
        return ingredientService.getSingleIngredient(ingredientIdentifier);
    }

    @GetMapping("/products/{productIdentifier}/ingredients")
    List<Ingredient> getIngredientsByProduct(@PathVariable Integer productIdentifier){
        return ingredientService.getIngredientsByProduct(productIdentifier);
    }

    @PostMapping("/ingredients")
    void createIngredient(@RequestBody Ingredient ingredient){
        ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/ingredients")
    void modifyIngredient(@RequestBody Ingredient ingredient){
        ingredientService.modifyIngredient(ingredient);
    }

    @DeleteMapping("/ingredients/{ingredientIdentifier}")
    void deleteIngredient(@PathVariable Integer ingredientIdentifier){
        ingredientService.deleteIngredient(ingredientIdentifier);
    }

}

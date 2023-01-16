package com.isl.lionelmaquet.burger2home.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    Ingredient createIngredient(@RequestBody Ingredient ingredient){

       return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/ingredients")
    @PreAuthorize("hasRole('ADMIN')")
    Ingredient modifyIngredient(@RequestBody Ingredient ingredient){
        return ingredientService.modifyIngredient(ingredient);
    }

    @DeleteMapping("/ingredients/{ingredientIdentifier}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteIngredient(@PathVariable Integer ingredientIdentifier){
        ingredientService.deleteIngredient(ingredientIdentifier);
    }

}

package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import com.isl.lionelmaquet.burger2home.Ingredient.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientTranslationController {

    @Autowired
    IngredientTranslationService ingredientTranslationService;

    @GetMapping("/ingredients/translations")
    List<IngredientTranslation> getAllIngredientTranslations(){
        return ingredientTranslationService.getAllIngredientTranslations();
    }

    @GetMapping("/ingredients/{ingredientIdentifier}/translations")
    List<IngredientTranslation> getTranslationsByIngredient(@PathVariable Integer ingredientIdentifier){
        return ingredientTranslationService.findByIngredient(ingredientIdentifier);
    }

    @GetMapping("/ingredients/translations/{ingredientTranslationIdentifier}")
    Optional<IngredientTranslation> getIngredientTranslation(@PathVariable Integer ingredientTranslationIdentifier){
        return ingredientTranslationService.findIngredientTranslation(ingredientTranslationIdentifier);
    }

    @PostMapping("/ingredients/translations")
    void createIngredientTranslation(@RequestBody IngredientTranslation ingredientTranslation){
        ingredientTranslationService.createIngredientTranslation(ingredientTranslation);
    }

    @PutMapping("/ingredients/translations")
    void modifyIngredientTranslation(@RequestBody IngredientTranslation ingredientTranslation){
        ingredientTranslationService.modifyIngredientTranslation(ingredientTranslation);
    }

    @DeleteMapping("/ingredients/translations/{ingredientTranslationIdentifier}")
    void deleteIngredientTranslation(@PathVariable Integer ingredientTranslationIdentifier){
        ingredientTranslationService.deleteIngredientTranslation(ingredientTranslationIdentifier);
    }


}

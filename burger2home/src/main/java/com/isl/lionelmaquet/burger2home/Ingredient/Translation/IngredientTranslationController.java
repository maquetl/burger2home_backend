package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.IngredientService;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class IngredientTranslationController {

    @Autowired
    IngredientTranslationService ingredientTranslationService;

    @GetMapping("/ingredients/translations")
    List<IngredientTranslation> getAllIngredientTranslations(@RequestParam(required = false) String language){
        return (List<IngredientTranslation>) TranslationUtils.filterTranslationsByLanguage(ingredientTranslationService.getAllIngredientTranslations(), language);
    }

    @GetMapping("/ingredients/{ingredientIdentifier}/translations")
    List<IngredientTranslation> getTranslationsByIngredient(@PathVariable Integer ingredientIdentifier, @RequestParam(required = false) String language){
        return (List<IngredientTranslation>) TranslationUtils.filterTranslationsByLanguage(ingredientTranslationService.findByIngredient(ingredientIdentifier), language);
    }

    @GetMapping("/ingredients/translations/{ingredientTranslationIdentifier}")
    Optional<IngredientTranslation> getIngredientTranslation(@PathVariable Integer ingredientTranslationIdentifier){
        return ingredientTranslationService.findIngredientTranslation(ingredientTranslationIdentifier);
    }

    @PostMapping("/ingredients/translations")
    @PreAuthorize("hasRole('ADMIN')")
    IngredientTranslation createIngredientTranslation(@RequestBody IngredientTranslation ingredientTranslation){
        return ingredientTranslationService.createIngredientTranslation(ingredientTranslation);
    }

    @PutMapping("/ingredients/translations")
    @PreAuthorize("hasRole('ADMIN')")
    IngredientTranslation modifyIngredientTranslation(@RequestBody IngredientTranslation ingredientTranslation){
        return ingredientTranslationService.modifyIngredientTranslation(ingredientTranslation);
    }

    @DeleteMapping("/ingredients/translations/{ingredientTranslationIdentifier}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteIngredientTranslation(@PathVariable Integer ingredientTranslationIdentifier){
        ingredientTranslationService.deleteIngredientTranslation(ingredientTranslationIdentifier);
    }


}

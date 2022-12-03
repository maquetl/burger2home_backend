package com.isl.lionelmaquet.burger2home.Allergen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AllergenController {

    @Autowired
    AllergenService allergenService;

    @GetMapping("/allergens")
    List<Allergen> getAllAllergens(){
        return allergenService.getAllAllergens();
    }

    @GetMapping("/ingredients/{ingredientIdentifier}/allergens")
    List<Allergen> getAllergensByIngredient(@PathVariable Integer ingredientIdentifier){
        return allergenService.getAllergensByIngredient(ingredientIdentifier);
    }

    @GetMapping("/allergens/{allergenIdentifier}")
    Optional<Allergen> getSingleAllergen(@PathVariable Integer allergenIdentifier){
        return allergenService.getSingleAllergen(allergenIdentifier);
    }

    @PostMapping("/allergens")
    void createSingleAllergen(@RequestBody Allergen allergen){
        allergenService.createAllergen(allergen);
    }

    @DeleteMapping("/allergens/{allergenIdentifier}")
    void deleteSingleAllergen(@PathVariable Integer allergenIdentifier){
        allergenService.deleteAllergen(allergenIdentifier);
    }

    @PutMapping("/allergens")
    void modifySingleAllergen(@RequestBody Allergen allergen){
        allergenService.modifyAllergen(allergen);
    }
}

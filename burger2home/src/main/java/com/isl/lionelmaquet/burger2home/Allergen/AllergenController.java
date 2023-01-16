package com.isl.lionelmaquet.burger2home.Allergen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.isl.lionelmaquet.burger2home.Utils.AuthUtils.preauth_admin;

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
    @PreAuthorize(preauth_admin)
    Allergen createSingleAllergen(@RequestBody Allergen allergen){
        return allergenService.createAllergen(allergen);
    }

    @DeleteMapping("/allergens/{allergenIdentifier}")
    @PreAuthorize(preauth_admin)
    void deleteSingleAllergen(@PathVariable Integer allergenIdentifier){
        allergenService.deleteAllergen(allergenIdentifier);
    }

    @PutMapping("/allergens")
    @PreAuthorize(preauth_admin)
    Allergen modifySingleAllergen(@RequestBody Allergen allergen){
        return allergenService.modifyAllergen(allergen);
    }
}

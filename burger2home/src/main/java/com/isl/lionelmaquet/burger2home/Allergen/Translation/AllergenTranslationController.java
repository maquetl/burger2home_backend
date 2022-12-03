package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AllergenTranslationController {

    @Autowired
    AllergenTranslationService allergenTranslationService;

    @GetMapping("/allergens/translations")
    List<AllergenTranslation> getAllAllergenTranslations(){
        return allergenTranslationService.getAllAllergenTranslations();
    }

    @GetMapping("/allergens/translations/{allergenTranslationIdentifier}")
    Optional<AllergenTranslation> getSingleAllergenTranslation(@PathVariable Integer allergenTranslationIdentifier){
        return allergenTranslationService.getSingleAllergenTranslation(allergenTranslationIdentifier);
    }

    @GetMapping("/allergens/{allergenIdentifier}/translations")
    List<AllergenTranslation> getAllergenTranslationsByAllergen(@PathVariable Integer allergenIdentifier){
        return allergenTranslationService.getAllergenTranslationByAllergen(allergenIdentifier);
    }

    @PostMapping("/allergens/translations")
    void createAllergenTranslation(@RequestBody AllergenTranslation allergenTranslation){
        allergenTranslationService.createAllergenTranslation(allergenTranslation);
    }

    @PutMapping("/allergens/translations")
    void modifyAllergenTranslation(@RequestBody AllergenTranslation allergenTranslation){
        allergenTranslationService.modifyAllergenTranslation(allergenTranslation);
    }

    @DeleteMapping("/allergens/translations/{allergenTranslationIdentifier}")
    void deleteAllergenTranslation(@PathVariable Integer allergenTranslationIdentifier){
        allergenTranslationService.deleteAllergenTranslation(allergenTranslationIdentifier);
    }
}

package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Translation;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.isl.lionelmaquet.burger2home.Utils.AuthUtils.preauth_admin;

@RestController
public class AllergenTranslationController {

    @Autowired
    AllergenTranslationService allergenTranslationService;

    @GetMapping("/allergens/translations")
    List<AllergenTranslation> getAllAllergenTranslations(@RequestParam(required = false) String language){
        return (List<AllergenTranslation>) TranslationUtils.filterTranslationsByLanguage(allergenTranslationService.getAllAllergenTranslations(), language);
    }

    @GetMapping("/allergens/translations/{allergenTranslationIdentifier}")
    AllergenTranslation getSingleAllergenTranslation(@PathVariable Integer allergenTranslationIdentifier){
        Optional<AllergenTranslation> allergenTranslation = allergenTranslationService.getSingleAllergenTranslation(allergenTranslationIdentifier);
        return allergenTranslation.orElse(null);
    }

    @GetMapping("/allergens/{allergenIdentifier}/translations")
    List<AllergenTranslation> getAllergenTranslationsByAllergen(@PathVariable Integer allergenIdentifier, @RequestParam(required = false) String language){
        return (List<AllergenTranslation>) TranslationUtils.filterTranslationsByLanguage(allergenTranslationService.getAllergenTranslationByAllergen(allergenIdentifier), language);
    }

    @PostMapping("/allergens/translations")
    @PreAuthorize(preauth_admin)
    AllergenTranslation createAllergenTranslation(@RequestBody AllergenTranslation allergenTranslation){
        return allergenTranslationService.createAllergenTranslation(allergenTranslation);
    }

    @PutMapping("/allergens/translations")
    @PreAuthorize(preauth_admin)
    AllergenTranslation modifyAllergenTranslation(@RequestBody AllergenTranslation allergenTranslation){
        return allergenTranslationService.modifyAllergenTranslation(allergenTranslation);
    }

    @DeleteMapping("/allergens/translations/{allergenTranslationIdentifier}")
    @PreAuthorize(preauth_admin)
    void deleteAllergenTranslation(@PathVariable Integer allergenTranslationIdentifier){
        allergenTranslationService.deleteAllergenTranslation(allergenTranslationIdentifier);
    }
}

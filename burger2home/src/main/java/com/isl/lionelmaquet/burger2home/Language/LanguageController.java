package com.isl.lionelmaquet.burger2home.Language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LanguageController {

    @Autowired
    LanguageService languageService;

    @GetMapping("/languages")
    List<Language> getAllLanguages(){
        return languageService.getAllLanguages();
    }

    @GetMapping("/languages/{languageIdentifier}")
    Optional<Language> getSingleLanguage(@PathVariable Integer languageIdentifier){
        return languageService.getSingleLanguage(languageIdentifier);
    }

    @PostMapping("/languages")
    @PreAuthorize("hasRole('ADMIN')")
    Language createLanguage(@RequestBody Language language){
        return languageService.createLanguage(language);
    }

    @PutMapping("/languages")
    @PreAuthorize("hasRole('ADMIN')")
    Language modifyLanguage(@RequestBody Language language){
        return languageService.modifyLanguage(language);
    }

    @DeleteMapping("/languages/{languageIdentifier}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteLanguage(@PathVariable Integer languageIdentifier){
        languageService.deleteLanguage(languageIdentifier);
    }

}

package com.isl.lionelmaquet.burger2home.Language;

import org.springframework.beans.factory.annotation.Autowired;
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
    void createLanguage(@RequestBody Language language){
        languageService.createLanguage(language);
    }

    @PutMapping("/languages")
    void modifyLanguage(@RequestBody Language language){
        languageService.modifyLanguage(language);
    }

    @DeleteMapping("/languages/{languageIdentifier}")
    void deleteLanguage(@PathVariable Integer languageIdentifier){
        languageService.deleteLanguage(languageIdentifier);
    }

}

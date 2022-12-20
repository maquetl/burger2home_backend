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
    Language createLanguage(@RequestBody Language language){
        return languageService.createLanguage(language);
    }

    @PutMapping("/languages")
    Language modifyLanguage(@RequestBody Language language){
        return languageService.modifyLanguage(language);
    }

    @DeleteMapping("/languages/{languageIdentifier}")
    void deleteLanguage(@PathVariable Integer languageIdentifier){
        languageService.deleteLanguage(languageIdentifier);
    }

}

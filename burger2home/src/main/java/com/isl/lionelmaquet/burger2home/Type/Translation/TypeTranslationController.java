package com.isl.lionelmaquet.burger2home.Type.Translation;

import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Type.Type;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TypeTranslationController {

    @Autowired
    TypeTranslationService typeTranslationService;

    @GetMapping("/types/{typeIdentifier}/translations")
    public List<TypeTranslation> geTypeTranslations(@PathVariable Integer typeIdentifier, @RequestParam(required = false) String language){
        List<TypeTranslation> translations = typeTranslationService.getByType(typeIdentifier);
        return (List<TypeTranslation>) TranslationUtils.filterTranslationsByLanguage(translations, language);
    }

    @GetMapping("/types/translations")
    public List<TypeTranslation> getAll(){
        return typeTranslationService.getAll();
    }

    @GetMapping("/types/translations/{typeTranslationIdentifier}")
    public TypeTranslation getSingle(@PathVariable Integer typeTranslationIdentifier){
        return typeTranslationService.getSingle(typeTranslationIdentifier);
    }

    @PostMapping("/types/translations")
    @PreAuthorize("hasRole('ADMIN')")
    public TypeTranslation create(@RequestBody TypeTranslation typeTranslation){
        return typeTranslationService.create(typeTranslation);
    }

    @PutMapping("/types/translations")
    @PreAuthorize("hasRole('ADMIN')")
    public TypeTranslation modify(@RequestBody TypeTranslation typeTranslation){
        return typeTranslationService.modify(typeTranslation);
    }

    @DeleteMapping("/types/translations/{translationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer translationId){
        typeTranslationService.deleteById(translationId);
    }

}

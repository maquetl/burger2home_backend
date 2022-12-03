package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductFamilyTranslationController {

    @Autowired
    ProductFamilyTranslationService productFamilyTranslationService;

    @GetMapping("/products/families/translations")
    List<ProductFamilyTranslation> getAllProductFamilyTranslations(){
        return productFamilyTranslationService.getAllProductFamilyTranslations();
    }

    @GetMapping("/products/families/translations/{productFamilyTranslationIdentifier}")
    Optional<ProductFamilyTranslation> getSingleProductFamilyTranslation(@PathVariable Integer productFamilyTranslationIdentifier){
        return productFamilyTranslationService.getSingleProductFamilyTranslation(productFamilyTranslationIdentifier);
    }

    @GetMapping("/products/families/{productFamilyIdentifier}/translations")
    List<ProductFamilyTranslation> getProductFamilyTranslationsByProductFamily(@PathVariable Integer productFamilyIdentifier){
        return productFamilyTranslationService.getProductFamilyTranslationsByProductFamily(productFamilyIdentifier);
    }

    @PostMapping("/products/families/translations")
    void createProductFamilyTranslation(@RequestBody ProductFamilyTranslation productFamilyTranslation){
        productFamilyTranslationService.createProductFamilyTranslation(productFamilyTranslation);
    }

    @PutMapping("/products/families/translations")
    void modifyProductFamilyTranslation(@RequestBody ProductFamilyTranslation productFamilyTranslation){
        productFamilyTranslationService.modifyProductFamilyTranslation(productFamilyTranslation);
    }

    @DeleteMapping("/products/families/translations/{productFamilyTranslationIdentifier}")
    void deleteSingleProductFamilyTranslation(@PathVariable Integer productFamilyTranslationIdentifier){
        productFamilyTranslationService.deleteSingleProductFamilyTranslation(productFamilyTranslationIdentifier);
    }

}

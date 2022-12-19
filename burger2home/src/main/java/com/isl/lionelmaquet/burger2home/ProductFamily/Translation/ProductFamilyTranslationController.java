package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductFamilyTranslationController {

    @Autowired
    ProductFamilyTranslationService productFamilyTranslationService;

    @GetMapping("/products/families/translations")
    List<ProductFamilyTranslation> getAllProductFamilyTranslations(@RequestParam(required = false) String language){
        return (List<ProductFamilyTranslation>) TranslationUtils.filterTranslationsByLanguage(productFamilyTranslationService.getAllProductFamilyTranslations(), language);
    }

    @GetMapping("/products/families/translations/{productFamilyTranslationIdentifier}")
    Optional<ProductFamilyTranslation> getSingleProductFamilyTranslation(@PathVariable Integer productFamilyTranslationIdentifier){
        return productFamilyTranslationService.getSingleProductFamilyTranslation(productFamilyTranslationIdentifier);
    }

    @GetMapping("/products/families/{productFamilyIdentifier}/translations")
    List<ProductFamilyTranslation> getProductFamilyTranslationsByProductFamily(@PathVariable Integer productFamilyIdentifier, @RequestParam(required = false) String language){
        return (List<ProductFamilyTranslation>) TranslationUtils.filterTranslationsByLanguage(productFamilyTranslationService.getProductFamilyTranslationsByProductFamily(productFamilyIdentifier), language);
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

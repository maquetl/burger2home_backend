package com.isl.lionelmaquet.burger2home.Product.Translation;

import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.Utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductTranslationController {

    @Autowired
    ProductTranslationService productTranslationService;

    @GetMapping("/products/{productIdentifier}/translations")
    public List<ProductTranslation> getProductTranslation(@PathVariable Integer productIdentifier, @RequestParam(required = false) String language){
        return (List<ProductTranslation>) TranslationUtils.filterTranslationsByLanguage(productTranslationService.getByProduct(productIdentifier), language);
    }

    @PostMapping("/products/translations")
    public void createProductTranslation(@RequestBody ProductTranslation productTranslation){
        productTranslationService.createProductTranslation(productTranslation);
    }

    @DeleteMapping("/products/translations/{translationId}")
    public void deleteProductTranslation(@PathVariable Integer translationId){
        productTranslationService.deleteById(translationId);
    }

    @PutMapping("/products/translations")
    public void modifyProductTranslation(@RequestBody ProductTranslation productTranslation){
        productTranslationService.modifyProductTranslation(productTranslation);
    }

}

package com.isl.lionelmaquet.burger2home.Product.Translation;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductTranslationController {

    @Autowired
    ProductTranslationService productTranslationService;

    @GetMapping("/admin/products/{productIdentifier}/translations")
    public List<ProductTranslation> getProductTranslation(@PathVariable Integer productIdentifier){
        return productTranslationService.getByProduct(productIdentifier);
    }

    @PostMapping("/admin/products/translations")
    public void createProductTranslation(@RequestBody ProductTranslation productTranslation){
        productTranslationService.createProductTranslation(productTranslation);
    }

    @DeleteMapping("/admin/products/translations/{translationId}")
    public void deleteProductTranslation(@PathVariable Integer translationId){
        productTranslationService.deleteById(translationId);
    }

    @PutMapping("/admin/products/translations")
    public void modifyProductTranslation(@RequestBody ProductTranslation productTranslation){
        productTranslationService.modifyProductTranslation(productTranslation);
    }

}

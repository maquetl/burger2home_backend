package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public List<ProductBO> Index(@RequestParam(defaultValue = "EN") String language,
                                 @RequestParam(defaultValue = "false") Boolean expandIngredients,
                                 @RequestParam(defaultValue = "false") Boolean expandAllergens,
                                 @RequestParam(defaultValue = "false") Boolean availableProductsOnly
                                 ){
        return productService.getAll(language, expandIngredients, expandAllergens, availableProductsOnly);
    }
}

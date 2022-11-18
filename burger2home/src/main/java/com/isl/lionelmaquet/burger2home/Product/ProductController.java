package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public List<ProductBO> Index(@RequestParam(defaultValue = "EN") String language,
                                 @RequestParam(defaultValue = "false") Boolean availableProductsOnly
                                 ){
        return productService.getAll(language, availableProductsOnly);
    }

    @GetMapping("/products/{productIdentifier}")
    public Optional<ProductBO> getSingleProduct(@PathVariable Integer productIdentifier,
                                                @RequestParam(defaultValue = "EN") String language){
        return productService.getSingle(productIdentifier, language);
    }
}

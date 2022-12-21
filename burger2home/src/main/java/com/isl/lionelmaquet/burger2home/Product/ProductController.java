package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products/summaries")
    public List<ProductBO> Index(@RequestParam(defaultValue = "EN") String language,
                                 @RequestParam(defaultValue = "false") Boolean availableProductsOnly,
                                 @RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers,
                                 @RequestParam(defaultValue = "false", name ="mustBeOnMenu") boolean onMenu
                                 ){
        return productService.getProductBOs(language, availableProductsOnly, productFamilyIdentifiers, onMenu);
    }

    @GetMapping("/products/summaries/{productIdentifier}")
    public Optional<ProductBO> getSingleProductBO(@PathVariable Integer productIdentifier,
                                                @RequestParam(defaultValue = "EN") String language){
        return productService.getSingleProductBO(productIdentifier, language);
    }

    @GetMapping("/products/{productIdentifier}")
    public Optional<Product> getSingleProduct(@PathVariable Integer productIdentifier){
        return productService.getSingleProduct(productIdentifier);
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers,
                                        @RequestParam(defaultValue = "false", name ="mustBeOnMenu") boolean onMenu,
                                     @RequestParam(defaultValue = "false") Boolean availableProductsOnly){
        return productService.getProducts(productFamilyIdentifiers, onMenu, availableProductsOnly);
    }

    @PostMapping("/products")
    public Product createNewProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @DeleteMapping("/products/{productIdentifier}")
    public void deleteProdut(@PathVariable Integer productIdentifier){
        productService.deleteProduct(productIdentifier);
    }

    @PutMapping("/products")
    public Product modifyProduct(@RequestBody Product product){
        return productService.modifyProduct(product);
    }

}

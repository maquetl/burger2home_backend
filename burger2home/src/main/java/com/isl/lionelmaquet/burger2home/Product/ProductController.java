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
                                 @RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers
                                 ){
        return productService.getAllProductBOs(language, availableProductsOnly, productFamilyIdentifiers);
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

//    @GetMapping("/products/families/products")
//    public List<Product> getProductsByFamily(@RequestParam(name = "productFamily") List<Integer> productFamilyIdentifiers){
//        return productService.getProductsByFamily(productFamilyIdentifiers);
//    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam(required = false, name = "productFamily") List<Integer> productFamilyIdentifiers){
        return productService.getProductsByFamily(productFamilyIdentifiers);
    }

    @PostMapping("/products")
    public void createNewProduct(@RequestBody Product product){
        if(product.getId() == null) productService.createProduct(product);
    }

    @DeleteMapping("/products/{productIdentifier}")
    public void deleteProdut(@PathVariable Integer productIdentifier){
        productService.deleteProduct(productIdentifier);
    }

    @PutMapping("/products")
    public void modifyProduct(@RequestBody Product product){
        productService.modifyProduct(product);
    }







}

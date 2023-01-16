package com.isl.lionelmaquet.burger2home.ProductFamily;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductFamilyController {

    @Autowired
    ProductFamilyService productFamilyService;

    @GetMapping("products/families")
    List<ProductFamily> getAllProductFamilies(){
        return productFamilyService.getAllProductFamilies();
    }

    @GetMapping("/products/families/{productFamilyIdentifier}")
    Optional<ProductFamily> getSingleProductFamily(@PathVariable Integer productFamilyIdentifier){
        return productFamilyService.getSingleProductFamily(productFamilyIdentifier);
    }

    @GetMapping("/products/{productIdentifier}/families")
    List<ProductFamily> getProductFamiliesByProduct(@PathVariable Integer productIdentifier){
        return productFamilyService.getProductFamiliesByProduct(productIdentifier);
    }

    @PostMapping("/products/families")
    @PreAuthorize("hasRole('ADMIN')")
    ProductFamily createProductFamily(@RequestBody ProductFamily productFamily){
        return productFamilyService.createProductFamily(productFamily);
    }

    @PutMapping("/products/families")
    @PreAuthorize("hasRole('ADMIN')")
    ProductFamily modifyProductFamily(@RequestBody ProductFamily productFamily){
        return productFamilyService.modifyProductFamily(productFamily);
    }

    @DeleteMapping("/products/families/{productFamilyIdentifier}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteProductFamily(@PathVariable Integer productFamilyIdentifier){
        productFamilyService.deleteProductFamily(productFamilyIdentifier);
    }


}

package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductBO> getAllProductBOs(String language, Boolean availableProductsOnly, Integer productFamilyId);
    Optional<ProductBO> getSingleProductBO(Integer productId, String language);
    Optional<Product> getSingleProduct(Integer productId);
    List<Product> getAllProducts();
    void createProduct(Product product);
    void deleteProduct(Integer productId);

    void modifyProduct(Product product);

    List<Product> getProductsByFamily(Integer productFamilyIdentifier);
}


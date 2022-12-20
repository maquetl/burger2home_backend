package com.isl.lionelmaquet.burger2home.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductBO> getAllProductBOs(String language, Boolean availableProductsOnly, List<Integer> productFamilyIdentifiers, boolean onMenu);
    Optional<ProductBO> getSingleProductBO(Integer productId, String language);
    Optional<Product> getSingleProduct(Integer productId);
    void createProduct(Product product);
    void deleteProduct(Integer productId);

    void modifyProduct(Product product);

    List<Product> getProducts(List<Integer> productFamilyIdentifier, boolean onMenu, boolean mustBeAvailable);
}


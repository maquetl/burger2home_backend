package com.isl.lionelmaquet.burger2home.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductBO> getProductBOs(String language, Boolean availableProductsOnly, List<Integer> productFamilyIdentifiers, boolean onMenu);
    Optional<ProductBO> getSingleProductBO(Integer productId, String language);
    Optional<Product> getSingleProduct(Integer productId);
    Product createProduct(Product product);
    void deleteProduct(Integer productId);

    Product modifyProduct(Product product);

    List<Product> getProducts(List<Integer> productFamilyIdentifier, boolean onMenu, boolean mustBeAvailable);
}


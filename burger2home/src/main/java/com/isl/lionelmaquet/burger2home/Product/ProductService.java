package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<ProductBO> getAll(String language, Boolean availableProductsOnly);
    Optional<ProductBO> getSingle(Integer productId, String language);
}


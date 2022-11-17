package com.isl.lionelmaquet.burger2home.Product;

import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    List<ProductBO> getAll(String language, Boolean expandIngredients, Boolean expandAllergens, Boolean availableProductsOnly);
}


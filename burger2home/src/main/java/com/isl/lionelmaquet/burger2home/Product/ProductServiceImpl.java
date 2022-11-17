package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Price.Price;
import com.isl.lionelmaquet.burger2home.Price.PriceRepository;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTranslationService productTranslationService;

    @Override
    public List<ProductBO> getAll(String language, Boolean expandIngredients, Boolean expandAllergens, Boolean availableProductsOnly) {

        List<ProductBO> productBOS = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        for(Product p : products){
            ProductBO pbo = new ProductBO();

            // STEP 1 : Get basic id and image url
            pbo.setId(p.getId());
            pbo.setImageUrl(p.getImageUrl());

            // STEP 2 : Get its name and description based on the language
            ProductTranslation productTranslation = productTranslationService.getByProductAndLanguage(p.getId(), language);
            pbo.setName(productTranslation.getName());
            pbo.setDescription(productTranslation.getDescription());


            // STEP 3 : Get its current price

            productBOS.add(pbo);
        }





        // STEP 4 : Get its current discount

        // STEP 5 : Get its status based on the stocks of its ingredients

        // STEP 6 : Optional : Get its ingredients

        // STEP 7 : Optional : Get its allergens



        /*
        for(ProductBO pbo : productBOS){
            List<Price> prices =  priceRepository.findPricesByProductId(pbo.product.getId());
            pbo.setPrices(prices);
        }*/

        return productBOS;

    }
}

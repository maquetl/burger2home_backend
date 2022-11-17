package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Price.Price;
import com.isl.lionelmaquet.burger2home.Price.PriceRepository;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationServiceImpl;
import com.isl.lionelmaquet.burger2home.Promotion.Promotion;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTranslationService productTranslationService;

    @Autowired
    PriceService priceService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    StockHistorizationService stockHistorizationService;

    @Override
    public List<ProductBO> getAll(String language, Boolean availableProductsOnly) {

        List<ProductBO> productBOS = new ArrayList<>();

        System.out.println("STEP 0");
        List<Product> products = productRepository.findAll();

        for(Product p : products){
            ProductBO pbo = new ProductBO();

            // STEP 1 : Get basic id and image url
            System.out.println("STEP 1");
            pbo.setId(p.getId());
            pbo.setImageUrl(p.getImageUrl());

            // STEP 2 : Get its status based on the stocks of its ingredients
            System.out.println("STEP 2");
            Boolean isAvailable = true;
            for (Ingredient i : p.getIngredients()){
                Integer amount = stockHistorizationService.getCurrentStock(i.getId()).getAmount();
                if( amount == 0){
                    isAvailable = false;
                    break;
                }
            }
            if (!isAvailable && availableProductsOnly) {
                continue;
            }
            pbo.setAvailable(isAvailable);


            // STEP 3 : Get its name and description based on the language
            System.out.println("STEP 3");
            ProductTranslation productTranslation = productTranslationService.getByProductAndLanguage(p.getId(), language);
            pbo.setName(productTranslation.getName());
            pbo.setDescription(productTranslation.getDescription());


            // STEP 4 : Get its current price
            System.out.println("STEP 4");
            Price currentPrice = priceService.getCurrentPriceByProductId(p.getId());
            pbo.setCurrentPrice(currentPrice.getAmount());

            // STEP 5 : Get its current discount (if present)
            System.out.println("STEP 5");
            Optional<Promotion> currentPromotion = promotionService.getCurrentPromotion(p);
            pbo.setCurrentDiscount(0f);
            currentPromotion.ifPresent((promo) -> pbo.setCurrentDiscount(promo.getAmount()));
            // Set the actual price
            Float actualPrice = pbo.getCurrentPrice() - (pbo.getCurrentPrice() * pbo.getCurrentDiscount() / 100);
            pbo.setActualPrice(actualPrice);

            // STEP 6 : Set ingredients
            System.out.println("STEP 6");
            for(Ingredient i : p.getIngredients()){
                String ingredientName = i.getIngredientTranslations().stream().filter((it) -> it.getLanguage().getAbbreviation().equals(language)).toList().get(0).getName();
                if(!pbo.getIngredients().contains(ingredientName)) {
                    pbo.getIngredients().add(ingredientName);
                }
            }


            // STEP 7 : Set allergens
            System.out.println("STEP 7");
            List<String> allergens = new ArrayList<>();

            for (Ingredient i : p.getIngredients()){
                for (Allergen a : i.getAllergens() ){
                    for (AllergenTranslation at : a.getAllergenTranslations()){
                        if(at.getLanguage().getAbbreviation().equals(language) && !allergens.contains(at.getName())){
                            allergens.add(at.getName());
                        }
                    }
                }
            }
            pbo.setAllergens(allergens);

            productBOS.add(pbo);
        }











        // STEP 7 : Optional : Get its allergens



        /*
        for(ProductBO pbo : productBOS){
            List<Price> prices =  priceRepository.findPricesByProductId(pbo.product.getId());
            pbo.setPrices(prices);
        }*/

        return productBOS;

    }
}

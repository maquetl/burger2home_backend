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

        List<Product> products = productRepository.findAll();

        for(Product p : products){
            ProductBO pbo = new ProductBO();

            // STEP 1 : Get basic id and image url
            pbo.setId(p.getId());
            pbo.setImageUrl(p.getImageUrl());

            // STEP 2 : Get its status based on the stocks of its ingredients
            Boolean isAvailable = isAvailable(p);
            if (!isAvailable && availableProductsOnly) {
                continue;
            }
            pbo.setAvailable(isAvailable);


            // STEP 3 : Get its name and description based on the language
            ProductTranslation productTranslation = productTranslationService.getByProductAndLanguage(p.getId(), language);
            pbo.setName(productTranslation.getName());
            pbo.setDescription(productTranslation.getDescription());


            // STEP 4 : Get its current price
            Price currentPrice = priceService.getCurrentPriceByProductId(p.getId());
            Float currentPriceAmount = currentPrice.getAmount();
            pbo.setCurrentPrice(currentPriceAmount);

            // STEP 5 : Get its current discount (if present)
            promotionService.getCurrentPromotion(p).ifPresentOrElse((promo) -> {
                Float promoAmount = promo.getAmount();
                pbo.setCurrentDiscount(promoAmount);
                pbo.setActualPrice(currentPriceAmount - (currentPriceAmount * promoAmount / 100));
            }, () -> {
                pbo.setCurrentDiscount(0f);
                pbo.setActualPrice(currentPriceAmount);
            });


            // STEP 6 : Map ingredients & allergens to DTO (already present in entity)
            for (Ingredient i : p.getIngredients()){
                String ingredientName = i.getIngredientTranslations().stream().filter((it) -> it.getLanguage().getAbbreviation().equals(language)).toList().get(0).getName();
                if(!pbo.getIngredients().contains(ingredientName)) {
                    pbo.getIngredients().add(ingredientName);
                }

                for (Allergen a : i.getAllergens() ){
                    for (AllergenTranslation at : a.getAllergenTranslations()){
                        if(at.getLanguage().getAbbreviation().equals(language) && !pbo.getAllergens().contains(at.getName())){
                            pbo.getAllergens().add(at.getName());
                        }
                    }
                }
            }

            productBOS.add(pbo);
        }
        return productBOS;
    }


    private Boolean isAvailable(Product product){
        for (Ingredient i : product.getIngredients()){
            Integer amount = stockHistorizationService.getCurrentStock(i.getId()).getAmount();
            if(amount == 0){
                return false;
            }
        }
        return true;
    }
}

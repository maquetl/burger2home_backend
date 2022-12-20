package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Price.Price;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationService;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamilyService;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTranslationService productTranslationService;

    @Autowired
    ProductFamilyService productFamilyService;

    PriceService priceService;

    PromotionService promotionService;

    @Autowired
    StockHistorizationService stockHistorizationService;

    @Autowired
    public void setPriceService(PriceService priceService){
        this.priceService = priceService;
    }

    @Autowired
    public void setPromotionService(PromotionService promotionService){
        this.promotionService = promotionService;
    }

    @Override
    public List<ProductBO> getAllProductBOs(String language, Boolean availableProductsOnly, List<Integer> productFamilyIdentifiers, boolean onMenu) {
        List<Product> products = getProducts(productFamilyIdentifiers, onMenu, availableProductsOnly);

        List<ProductBO> productBOS = new ArrayList<>();

        for(Product product : products){
            ProductBO pbo = getProductBO(product, language);
            productBOS.add(pbo);
        }

        return productBOS;
    }

    @Override
    public Optional<ProductBO> getSingleProductBO(Integer productId, String language) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()){
            product.get().isAvailable = isAvailable(product.get());
            return Optional.ofNullable(getProductBO(product.get(), language));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> getSingleProduct(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        product.get().isAvailable = isAvailable(product.get());
        return product;
    }

    @Override
    public Product createProduct(Product product) {

        productRepository.save(product);

        priceService.createDefaultCurrentPrice(product.getId());

        return product;
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product modifyProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts(List<Integer> productFamilyIdentifiers, boolean onMenu, boolean mustBeAvailable) {
        Predicate<ProductFamily> isFromOneOfFamilies = new Predicate<ProductFamily>() {
            @Override
            public boolean test(ProductFamily productFamily) {
                return productFamilyIdentifiers.stream().anyMatch(pfi -> productFamily.getId() == pfi);
            }
        };

        Predicate<Product> hasOneOfFamilies = new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getProductFamilies().stream().anyMatch(isFromOneOfFamilies);
            }
        };

        // Set the calculated available property on all products.
        List<Product> products = productRepository.findAll();
        for (Product p : products){
            p.isAvailable = isAvailable(p);
        }

        if (mustBeAvailable){
            products = products.stream().filter(p -> {
                return p.isAvailable;
            }).toList();
        }

        if (productFamilyIdentifiers !=  null && productFamilyIdentifiers.size() > 0){
            products = products.stream().filter(hasOneOfFamilies).toList();
        }

        if (onMenu){
            products = products.stream().filter(p -> {
                return p.getOnMenu() == true;
            }).toList();
        }

        return products;
    }



    private ProductBO getProductBO(Product p, String languageAbbr){
        ProductBO pbo = new ProductBO();

        // STEP 2 : map attributes
        pbo.setId(p.getId());
        pbo.setImageUrl(p.getImageUrl());
        pbo.setOnMenu(p.getOnMenu());
        pbo.setAvailable(p.isAvailable);

        // STEP 3 : Get its name and description based on the language
        Optional<ProductTranslation> productTranslation = productTranslationService.getByProductAndLanguage(p.getId(), languageAbbr);
        productTranslation.ifPresent(pt -> {
            pbo.setName(pt.getName());
            pbo.setDescription(pt.getDescription());
        });


        // STEP 4 : Get its current price
        Optional<Price> currentPrice = priceService.getCurrentPriceByProductId(p.getId());
        Float currentPriceAmount = currentPrice.isPresent() ? currentPrice.get().getAmount() : null;
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
            String ingredientName = i.getIngredientTranslations().stream().filter((it) -> it.getLanguage().getAbbreviation().equals(languageAbbr)).toList().get(0).getName();
            if(!pbo.getIngredients().contains(ingredientName)) {
                pbo.getIngredients().add(ingredientName);
            }

            for (Allergen a : i.getAllergens() ){
                for (AllergenTranslation at : a.getAllergenTranslations()){
                    if(at.getLanguage().getAbbreviation().equals(languageAbbr) && !pbo.getAllergens().contains(at.getName())){
                        pbo.getAllergens().add(at.getName());
                    }
                }
            }
        }

        // STEP 7 : Map product families to DTO
        for(ProductFamily pf : p.getProductFamilies()){
            pbo.getProductFamilies().add(pf.getId());
        }

        return pbo;
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

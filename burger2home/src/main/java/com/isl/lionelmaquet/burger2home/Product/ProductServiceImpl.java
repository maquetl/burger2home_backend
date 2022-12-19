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
    public List<ProductBO> getAllProductBOs(String language, Boolean availableProductsOnly, List<Integer> productFamilyIdentifiers) {
        List<Product> products = getProductsByFamily(productFamilyIdentifiers);

        List<ProductBO> productBOS = new ArrayList<>();

        for(Product product : products){
            getProductBO(product, language, availableProductsOnly).ifPresent(pbo -> productBOS.add(pbo));
        }
        return productBOS;
    }

    @Override
    public Optional<ProductBO> getSingleProductBO(Integer productId, String language) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()){
            return getProductBO(product.get(), language, false);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> getSingleProduct(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void modifyProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getProductsByFamily(List<Integer> productFamilyIdentifiers) {
        List<Product> products = productRepository.findAll();
        if (productFamilyIdentifiers.size() == 0 || productFamilyIdentifiers == null) return products;
        return products.stream().filter(p -> p.getProductFamilies().stream().anyMatch(pf -> productFamilyIdentifiers.stream().anyMatch(pfi -> pf.getId() == pfi))).toList();
    }

    // This method returns an empty optional if the boolean mustBeAvailable is set to true and the product is not available
    private Optional<ProductBO> getProductBO(Product p, String languageAbbr, Boolean mustBeAvailable){
        ProductBO pbo = new ProductBO();

        // STEP 1 : Get its status based on the stocks of its ingredients
        Boolean isAvailable = isAvailable(p);
        if (!isAvailable && mustBeAvailable) {
            return Optional.empty();
        }
        pbo.setAvailable(isAvailable);

        // STEP 2 : Get basic id and image url
        pbo.setId(p.getId());
        pbo.setImageUrl(p.getImageUrl());

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

        return Optional.of(pbo);
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

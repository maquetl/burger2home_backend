package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Price.Price;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslationService;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamilyService;
import com.isl.lionelmaquet.burger2home.Promotion.Promotion;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTranslationService productTranslationService;

    @Autowired
    ProductFamilyService productFamilyService;

    @Autowired
    PriceService priceService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    StockHistorizationService stockHistorizationService;

    @Override
    public List<ProductBO> getProductBOs(String language, Boolean availableProductsOnly, List<Integer> productFamilyIdentifiers, boolean onMenu) {
        // Get a list of products matching the criterias
        List<Product> products = getProducts(productFamilyIdentifiers, onMenu, availableProductsOnly);

        // for each of them, build a productBO
        return products.stream().map(product -> buildProductBOFromProduct(product, language)).toList();
    }

    @Override
    public Optional<ProductBO> getSingleProductBO(Integer productId, String language) {
        // Get a single product
        Optional<Product> product = getSingleProduct(productId);

        // Build a product BO
        if (product.isPresent()){
            return Optional.ofNullable(buildProductBOFromProduct(product.get(), language));
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
    public List<Product> getProducts(List<Integer> productFamilyIdentifiers, boolean onMenu, boolean mustBeAvailable) {

        // predicate to test that a family is contained in a list of families
        Predicate<ProductFamily> isFromOneOfFamilies = new Predicate<ProductFamily>() {
            @Override
            public boolean test(ProductFamily productFamily) {
                return productFamilyIdentifiers.stream().anyMatch(pfi -> productFamily.getId() == pfi);
            }
        };

        // predicate to test that a product has any family that match the predicate "isFromOneOfFamilies"
        Predicate<Product> hasOneOfFamilies = new Predicate<Product>() {
            @Override
            public boolean test(Product product) {
                return product.getProductFamilies().stream().anyMatch(isFromOneOfFamilies);
            }
        };

        // Get all products
        List<Product> products = productRepository.findAll();

        // Set the calculated available property on all products.
        for (Product p : products) p.isAvailable = isAvailable(p);

        // Filter products on the available criteria if required
        if (mustBeAvailable) products = products.stream().filter(p -> p.isAvailable).toList();

        // Filter products based on the productFamily condition
        if (productFamilyIdentifiers !=  null && productFamilyIdentifiers.size() > 0){
            products = products.stream().filter(hasOneOfFamilies).toList();
        }

        // Filter products based on the onMenu criteria
        if (onMenu) products = products.stream().filter(p -> p.getOnMenu()).toList();

        // Return the filtered list
        return products;
    }

    private ProductBO buildProductBOFromProduct(Product p, String languageAbbr){
        ProductBO pbo = new ProductBO();

        // map product attributes to pbo
        mapProductAttributesToProductBO(p, pbo);

        // Set its name and description based on the language
        productTranslationService.getByProductAndLanguage(p.getId(), languageAbbr).ifPresent(pt -> mapProductTranslationAttributesToProductBO(pt, pbo));

        // Set its current price.
        pbo.setCurrentPrice(getCurrentPrice(p.getId()));

        // Set its current discount (if present)
        Optional<Promotion> currentPromo = promotionService.getCurrentPromotion(p.getId());
        if (currentPromo.isPresent()) pbo.setCurrentDiscount(currentPromo.get().getAmount());

        // Set its actual price
        pbo.setActualPrice(getActualPrice(pbo));

        // Set ingredients and allergens to PBO considering the required language
        mapIngredientAndAllergensTranslationsToProductBO(p.getIngredients(), languageAbbr, pbo);

        // Map product families to DTO
        for (ProductFamily pf : p.getProductFamilies()) pbo.getProductFamilies().add(pf.getId());


        return pbo;
    }

    private void mapIngredientAndAllergensTranslationsToProductBO(Set<Ingredient> ingredients, String languageAbbr, ProductBO pbo) {
        for (Ingredient i : ingredients){
            String ingredientName = "";
            List<IngredientTranslation> its = i.getIngredientTranslations().stream().filter((it) -> it.getLanguage().getAbbreviation().equals(languageAbbr)).toList();
            if (its.size() > 0) ingredientName = its.get(0).getName();

            if(!ingredientName.equals("") && !pbo.getIngredients().contains(ingredientName)) {
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
    }

    private Float getActualPrice(ProductBO pbo) {
        if (pbo.getCurrentPrice() == null) return null;
        if (pbo.getCurrentDiscount() == null) return pbo.getCurrentPrice();
        return pbo.getCurrentPrice() - (pbo.getCurrentPrice() * pbo.getCurrentDiscount() / 100);
    }

    private Float getCurrentPrice(Integer productId){
        Optional<Price> currentPrice = priceService.getCurrentPriceByProductId(productId);
        Float currentPriceAmount = currentPrice.isPresent() ? currentPrice.get().getAmount() : null;
        return currentPriceAmount;
    }

    private void mapProductTranslationAttributesToProductBO(ProductTranslation productTranslation, ProductBO pbo){
        pbo.setName(productTranslation.getName());
        pbo.setDescription(productTranslation.getDescription());
    }

    private void mapProductAttributesToProductBO(Product p, ProductBO pbo) {
        pbo.setId(p.getId());
        pbo.setImageName(p.getImageName());
        pbo.setOnMenu(p.getOnMenu());
        pbo.setAvailable(p.isAvailable);
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

    @Override
    public Product modifyProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product createProduct(Product product) {
        productRepository.save(product);
        priceService.createDefaultCurrentPrice(product.getId());
        return product;
    }
}

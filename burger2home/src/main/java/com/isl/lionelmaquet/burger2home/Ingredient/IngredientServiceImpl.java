package com.isl.lionelmaquet.burger2home.Ingredient;

import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslationService;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorization;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientTranslationService ingredientTranslationService;

    @Autowired
    StockHistorizationService stockHistorizationService;

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll().stream().map(i -> {
            Integer currentStock = stockHistorizationService.getCurrentStock(i.getId()).getAmount();
            i.setCurrentStock(currentStock);
            return i;
        }).toList();
    }

    @Override
    public Optional<Ingredient> getSingleIngredient(Integer ingredientIdentifier) {
        Optional<Ingredient> i = ingredientRepository.findById(ingredientIdentifier);
        if (i.isPresent()){
            Integer currentStock = stockHistorizationService.getCurrentStock(i.get().getId()).getAmount();
            i.get().setCurrentStock(currentStock);
        }
        return i;
    }

    @Override
    public List<Ingredient> getIngredientsByProduct(Integer productIdentifier) {
        return ingredientRepository.findByProductsId(productIdentifier).stream().map(i -> {
            Integer currentStock = stockHistorizationService.getCurrentStock(i.getId()).getAmount();
            i.setCurrentStock(currentStock);
            return i;
        }).toList();
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        Ingredient i = ingredientRepository.save(ingredient);

        StockHistorization newStock = new StockHistorization();
        newStock.setIngredientId(i.getId());
        newStock.setAmount(0);
        newStock.setCreationDate(Instant.now());
        stockHistorizationService.createStockHistorization(newStock);


        return i;
    }

    @Override
    public Ingredient modifyIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public void deleteIngredient(Integer ingredientIdentifier) {
        Ingredient ingredient = ingredientRepository.findById(ingredientIdentifier).get();

        // deleting an ingredient will delete its translations
        for(IngredientTranslation it : ingredient.getIngredientTranslations()){
            ingredientTranslationService.deleteIngredientTranslation(it.getId());
        }

        // it will also delete itself from all the products that are linked to it.
        for (Product product : ingredient.getProducts()){
            product.getIngredients().remove(ingredient);
        }

        // it will also delete all stock historizations linked to this ingredient
        for (StockHistorization stock : stockHistorizationService.getStockHistorizationsByIngredient(ingredientIdentifier)){
            stockHistorizationService.deleteStockHistorization(stock.getId());
        }

        // Finally, it deletes the ingredient itself
        ingredientRepository.deleteById(ingredientIdentifier);
    }
}

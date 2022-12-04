package com.isl.lionelmaquet.burger2home.StockHistorization;

import java.util.List;
import java.util.Optional;

public interface StockHistorizationService {
    StockHistorization getCurrentStock(Integer ingredientId);

    List<StockHistorization> getAllStockHistorizations();

    Optional<StockHistorization> getSingleStockHistorization(Integer stockHistorizationIdentifier);

    void createStockHistorization(StockHistorization stockHistorization);

    void modifyStockHistorization(StockHistorization stockHistorization);

    void deleteStockHistorization(Integer stockHistorizationIdentifier);

    List<StockHistorization> getStockHistorizationsByIngredient(Integer ingredientIdentifier);
}

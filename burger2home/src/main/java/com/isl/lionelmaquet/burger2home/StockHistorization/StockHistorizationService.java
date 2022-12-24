package com.isl.lionelmaquet.burger2home.StockHistorization;

import java.util.List;
import java.util.Optional;

public interface StockHistorizationService {
    StockHistorization getCurrentStock(Integer ingredientId);

    List<StockHistorization> getAllStockHistorizations();

    Optional<StockHistorization> getSingleStockHistorization(Integer stockHistorizationIdentifier);

    StockHistorization createStockHistorization(StockHistorization stockHistorization);

    StockHistorization modifyStockHistorization(StockHistorization stockHistorization);

    List<StockHistorization> getStockHistorizationsByIngredient(Integer ingredientIdentifier);

    void deleteStockHistorization(Integer stockHistorizationIdentifier);
}

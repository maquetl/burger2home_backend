package com.isl.lionelmaquet.burger2home.StockHistorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockHistorizationServiceImpl implements StockHistorizationService {

    @Autowired
    StockHistorizationRepository stockHistorizationRepository;

    @Override
    public StockHistorization getCurrentStock(Integer ingredientId) {
        List<StockHistorization> stocks = stockHistorizationRepository.findTopByIngredientIdOrderByCreationDateDesc(ingredientId);
        return stocks.get(0);
    }

    @Override
    public List<StockHistorization> getAllStockHistorizations() {
        return stockHistorizationRepository.findAll();
    }

    @Override
    public Optional<StockHistorization> getSingleStockHistorization(Integer stockHistorizationIdentifier) {
        return stockHistorizationRepository.findById(stockHistorizationIdentifier);
    }

    @Override
    public StockHistorization createStockHistorization(StockHistorization stockHistorization) {
        return stockHistorizationRepository.save(stockHistorization);
    }

    @Override
    public StockHistorization modifyStockHistorization(StockHistorization stockHistorization) {
        return stockHistorizationRepository.save(stockHistorization);
    }

    @Override
    public void deleteStockHistorization(Integer stockHistorizationIdentifier) {
        stockHistorizationRepository.deleteById(stockHistorizationIdentifier);
    }

    @Override
    public List<StockHistorization> getStockHistorizationsByIngredient(Integer ingredientIdentifier) {
        return stockHistorizationRepository.findByIngredientId(ingredientIdentifier);
    }
}

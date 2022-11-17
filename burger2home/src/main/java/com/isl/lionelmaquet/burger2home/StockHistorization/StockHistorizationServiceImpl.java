package com.isl.lionelmaquet.burger2home.StockHistorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockHistorizationServiceImpl implements StockHistorizationService {

    @Autowired
    StockHistorizationRepository stockHistorizationRepository;

    @Override
    public StockHistorization getCurrentStock(Integer ingredientId) {
        List<StockHistorization> stocks = stockHistorizationRepository.findTopByIngredientIdOrderByCreationDateDesc(ingredientId);
        return stocks.get(0);
    }
}

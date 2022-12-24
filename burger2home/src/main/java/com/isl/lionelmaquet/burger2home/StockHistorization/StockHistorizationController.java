package com.isl.lionelmaquet.burger2home.StockHistorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StockHistorizationController {

    @Autowired
    StockHistorizationService serv;

    @GetMapping("/stocks")
    List<StockHistorization> getAllStockHistorizations(){
        return serv.getAllStockHistorizations();
    }

    @GetMapping("/stocks/{stockHistorizationIdentifier}")
    Optional<StockHistorization> getSingleStockHistorization(@PathVariable Integer stockHistorizationIdentifier){
        return serv.getSingleStockHistorization(stockHistorizationIdentifier);
    }

    @GetMapping("/ingredients/{ingredientIdentifier}/stocks")
    List<StockHistorization> getStockHistorizationsByProduct(@PathVariable Integer ingredientIdentifier){
        return serv.getStockHistorizationsByIngredient(ingredientIdentifier);
    }

    @PostMapping("/stocks")
    StockHistorization createStockHistorization(@RequestBody StockHistorization stockHistorization){
        return serv.createStockHistorization(stockHistorization);
    }

    @PutMapping("/stocks")
    StockHistorization modifyStockHistorization(@RequestBody StockHistorization stockHistorization){
        return serv.modifyStockHistorization(stockHistorization);
    }
}

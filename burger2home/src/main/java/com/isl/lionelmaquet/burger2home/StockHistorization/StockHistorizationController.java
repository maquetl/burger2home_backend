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
    void createStockHistorization(@RequestBody StockHistorization stockHistorization){
        serv.createStockHistorization(stockHistorization);
    }

    @PutMapping("/stocks")
    void modifyStockHistorization(@RequestBody StockHistorization stockHistorization){
        serv.modifyStockHistorization(stockHistorization);
    }

    @DeleteMapping("/stocks/{stockHistorizationIdentifier}")
    void deleteStockHistorization(@PathVariable Integer stockHistorizationIdentifier){
        serv.deleteStockHistorization(stockHistorizationIdentifier);
    }
}

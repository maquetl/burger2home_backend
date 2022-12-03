package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PriceController {

    @Autowired
    PriceService priceService;

    @GetMapping("/prices")
    List<Price> getAllPrices(){
        return priceService.getAllPrices();
    }

    @GetMapping("/prices/{priceIdentifier}")
    Optional<Price> getSinglePrice(@PathVariable Integer priceIdentifier){
        return priceService.getSinglePrice(priceIdentifier);
    }

    @GetMapping("/products/{productIdentifier}/prices")
    List<Price> getPricesByProduct(@PathVariable Integer productIdentifier){
        return priceService.getAllPricesByProduct(productIdentifier);
    }

    @PostMapping("/prices")
    void createPrice(@RequestBody Price price){
        priceService.createPrice(price);
    }

    @PutMapping("/prices")
    void modifyPrice(@RequestBody Price price){
        priceService.modifyPrice(price);
    }

    @DeleteMapping("/prices/{priceIdentifier}")
    void deletePrice(@PathVariable Integer priceIdentifier){
        priceService.deletePrice(priceIdentifier);
    }

}

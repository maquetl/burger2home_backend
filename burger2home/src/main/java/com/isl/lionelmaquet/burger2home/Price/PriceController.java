package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/products/{productIdentifier}/prices/current")
    Price getCurrentPrice(@PathVariable Integer productIdentifier){
        return priceService.getCurrentPriceByProductId(productIdentifier).get();
    }

    @GetMapping("/products/{productIdentifier}/prices/next")
    @PreAuthorize("hasRole('ADMIN')")
    Optional<Price> getNextPrice(@PathVariable Integer productIdentifier){
        return priceService.getNextPrice(productIdentifier);
    }

    @PostMapping("/products/{productIdentifier}/prices/current")
    @PreAuthorize("hasRole('ADMIN')")
    Price setCurrentPrice(@PathVariable Integer productIdentifier, @RequestBody Price price){
        price.setProductId(productIdentifier);
        return priceService.setCurrentPrice(price);
    }

    @PostMapping("/products/{productIdentifier}/prices/next")
    @PreAuthorize("hasRole('ADMIN')")
    Price setNextPrice(@PathVariable Integer productIdentifier, @RequestBody Price price){
        price.setProductId(productIdentifier);
        return priceService.setNextPrice(price);
    }
}

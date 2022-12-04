package com.isl.lionelmaquet.burger2home.BasketLine;

import com.isl.lionelmaquet.burger2home.Basket.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BasketLineController {

    @Autowired
    BasketLineService serv;

    @GetMapping("/basketLines")
    List<BasketLine> getAllBasketLines(){
        return serv.getAllBasketLines();
    }

    @GetMapping("/basketLines/{basketLineIdentifier}")
    Optional<BasketLine> getSingleBasketLine(@PathVariable Integer basketLineIdentifier){
        return serv.getSingleBasketLine(basketLineIdentifier);
    }

    @GetMapping("/baskets/{basketIdentifier}/basketLines")
    List<BasketLine> getBasketLinesByBasket(@PathVariable Integer basketIdentifier){
        return serv.getBasketLinesByBasket(basketIdentifier);
    }

    @PostMapping("/basketLines")
    void createBasketLine(@RequestBody BasketLine basketLine){
        serv.createBasketLine(basketLine);
    }

    @PutMapping("/basketLines")
    void modifyBasketLine(@RequestBody BasketLine basketLine){
        serv.modifyBasketLine(basketLine);
    }

    @DeleteMapping("/basketLines/{basketLineIdentifier}")
    void deleteBasketLine(@PathVariable Integer basketLineIdentifier){
        serv.deleteBasketLine(basketLineIdentifier);
    }
}

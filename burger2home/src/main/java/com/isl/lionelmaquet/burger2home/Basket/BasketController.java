package com.isl.lionelmaquet.burger2home.Basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BasketController {

    @Autowired
    BasketService serv;

    @GetMapping("/baskets")
    List<Basket> getAllBaskets(){
        return serv.getAllBaksets();
    }

    @GetMapping("/baskets/{basketIdentifier}")
    Optional<Basket> getSingleBasket(@PathVariable Integer basketIdentifier){
        return serv.getSingleBasket(basketIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/basket")
    Optional<Basket> getBasketByUser(@PathVariable Integer userIdentifier){
        return serv.getBasketByUser(userIdentifier);
    }

    @PostMapping("/baskets")
    Basket createBasket(@RequestBody Basket basket){
        return serv.createBasket(basket);
    }

    @PutMapping("/baskets")
    Basket modifyBasket(@RequestBody Basket basket){
        return serv.modifyBasket(basket);
    }

    @DeleteMapping("/baskets/{basketIdentifier}")
    void deleteBasket(@PathVariable Integer basketIdentifier){
        serv.deleteBasket(basketIdentifier);
    }
}

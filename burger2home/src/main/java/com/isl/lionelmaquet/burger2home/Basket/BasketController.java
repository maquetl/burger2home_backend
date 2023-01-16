package com.isl.lionelmaquet.burger2home.Basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.isl.lionelmaquet.burger2home.Utils.AuthUtils.preauth_admin;

@RestController
public class BasketController {

    @Autowired
    BasketService serv;

    @GetMapping("/baskets")
    @PreAuthorize(preauth_admin)
    List<Basket> getAllBaskets(){
        return serv.getAllBaksets();
    }

    @GetMapping("/baskets/{basketIdentifier}")
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("returnObject != null ? returnObject.userId == authentication.principal.id or hasRole('ADMIN') : hasRole('ADMIN')")
    Basket getSingleBasket(@PathVariable Integer basketIdentifier){
        Optional<Basket> basket = serv.getSingleBasket(basketIdentifier);
        return basket.orElse(null);
    }

    @GetMapping("/users/{userIdentifier}/basket")
    @PreAuthorize("#userIdentifier == authentication.principal.id or hasRole('ADMIN')")
    Optional<Basket> getBasketByUser(@PathVariable Integer userIdentifier){
        return serv.getBasketByUser(userIdentifier);
    }

    @PostMapping("/baskets")
    @PreAuthorize("#basket.userId == authentication.principal.id or hasRole('ADMIN')")
    Basket createBasket(@RequestBody Basket basket){
        return serv.createBasket(basket);
    }

    @PutMapping("/baskets")
    @PreAuthorize("#basket.userId == authentication.principal.id or hasRole('ADMIN')")
    Basket modifyBasket(@RequestBody Basket basket){
        return serv.modifyBasket(basket);
    }
}

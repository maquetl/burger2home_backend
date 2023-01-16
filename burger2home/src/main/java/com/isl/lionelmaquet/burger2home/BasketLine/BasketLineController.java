package com.isl.lionelmaquet.burger2home.BasketLine;

import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.Utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class BasketLineController {

    @Autowired
    BasketService basketService;

    @Autowired
    BasketLineService serv;

    @GetMapping("/basketLines")
    @PreAuthorize("hasRole('ADMIN')")
    List<BasketLine> getAllBasketLines(){
        return serv.getAllBasketLines();
    }

    @GetMapping("/basketLines/{basketLineIdentifier}")
    @PreAuthorize("isAuthenticated()")
    Optional<BasketLine> getSingleBasketLine(@PathVariable Integer basketLineIdentifier){
        Optional<BasketLine> basketLine = serv.getSingleBasketLine(basketLineIdentifier);

        // Checks that the basket line's basket belongs to the connected user or that the current user is admin
        if(basketLine.isPresent()){
            Basket basket = basketService.getSingleBasket(basketLine.get().getBasketId().intValue()).get();
            if (Long.valueOf(basket.getUserId()) != AuthUtils.getCurrentUserId() && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }


        return basketLine;
    }

    @GetMapping("/baskets/{basketIdentifier}/basketLines")
    @PreAuthorize("isAuthenticated()")
    List<BasketLine> getBasketLinesByBasket(@PathVariable Integer basketIdentifier){

        // Checks that the basket line's basket belongs to the connected user or that the current user is admin
        basketService.getSingleBasket(basketIdentifier).ifPresent(b -> {
            if (Long.valueOf(b.getUserId()) != AuthUtils.getCurrentUserId() && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return serv.getBasketLinesByBasket(basketIdentifier);
    }

    @PostMapping("/basketLines")
    @PreAuthorize("isAuthenticated()")
    BasketLine createBasketLine(@RequestBody BasketLine basketLine){

        // Checks that the basket line's basket belongs to the connected user or that the current user is admin
        basketService.getSingleBasket(basketLine.getBasketId().intValue()).ifPresent(basket -> {
            if (Long.valueOf(basket.getUserId()) != AuthUtils.getCurrentUserId() && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return serv.createBasketLine(basketLine);
    }

    @PutMapping("/basketLines")
    @PreAuthorize("isAuthenticated()")
    BasketLine modifyBasketLine(@RequestBody BasketLine basketLine){

        // Checks that the basket line's basket belongs to the connected user or that the current user is admin
        basketService.getSingleBasket(basketLine.getBasketId().intValue()).ifPresent(basket -> {
            if (Long.valueOf(basket.getUserId()) != AuthUtils.getCurrentUserId() && !AuthUtils.currentUserIsAdmin()){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        });

        return serv.modifyBasketLine(basketLine);
    }

    @DeleteMapping("/basketLines/{basketLineIdentifier}")
    @PreAuthorize("isAuthenticated()")
    void deleteBasketLine(@PathVariable Integer basketLineIdentifier){

        // Checks that the basket line's basket belongs to the connected user or that the current user is admin
        serv.getSingleBasketLine(basketLineIdentifier).ifPresent(bl -> {
            basketService.getSingleBasket(bl.getBasketId().intValue()).ifPresent(basket -> {
                if (Long.valueOf(basket.getUserId()) != AuthUtils.getCurrentUserId() && !AuthUtils.currentUserIsAdmin()){
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
                }
            });
        });

        serv.deleteBasketLine(basketLineIdentifier);
    }
}

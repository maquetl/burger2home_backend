package com.isl.lionelmaquet.burger2home.Promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PromotionController {

    @Autowired
    PromotionService serv;

    @GetMapping("/promotions")
    List<Promotion> getAllPromotions(){
        return serv.getAllPromotions();
    }

    @GetMapping("/promotions/{promotionIdentifier}")
    Optional<Promotion> getSinglePromotion(@PathVariable Integer promotionIdentifier){
        return serv.getSinglePromotion(promotionIdentifier);
    }

    @PostMapping("/promotions")
    void createSinglePromotion(@RequestBody Promotion promotion){
        serv.createSinglePromotion(promotion);
    }

    @PutMapping("/promotions")
    void modifySinglePromotion(@RequestBody Promotion promotion){
        serv.modifySinglePromotion(promotion);
    }

    @DeleteMapping("/promotions/{promotionIdentifier}")
    void deleteSinglePromotion(@PathVariable Integer promotionIdentifier){
        serv.deleteSinglePromotion(promotionIdentifier);
    }
}

package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Promotion.Translation.PromotionTranslation;
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
    Promotion createSinglePromotion(@RequestBody Promotion promotion){
        return serv.createSinglePromotion(promotion);
    }

    @PutMapping("/promotions")
    Promotion modifySinglePromotion(@RequestBody Promotion promotion){
        return serv.modifySinglePromotion(promotion);
    }
}

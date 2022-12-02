package com.isl.lionelmaquet.burger2home.CreditCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/creditcards/{creditCardIdentifier}")
    Optional<CreditCard> getSingleCreditCard(@PathVariable Integer creditCardIdentifier){
        return creditCardService.getCreditCard(creditCardIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/creditcards")
    List<CreditCard> getCreditCardsByUser(@PathVariable Integer userIdentifier){
        return creditCardService.getCreditCardsByUser(userIdentifier);
    }

    @PostMapping("/creditcards")
    void createCreditCard(@RequestBody CreditCard creditCard){
        creditCardService.createCreditCard(creditCard);
    }

    @PutMapping("/creditcards")
    void modifyCreditCard(@RequestBody CreditCard creditCard){
        creditCardService.modifyCreditCard(creditCard);
    }

    @DeleteMapping("/creditcards/{creditCardIdentifier}")
    void deleteCreditCard(@PathVariable Integer creditCardIdentifier){
        creditCardService.deleteCreditCard(creditCardIdentifier);
    }
}

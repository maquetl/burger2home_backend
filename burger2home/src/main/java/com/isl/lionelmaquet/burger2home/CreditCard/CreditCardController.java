package com.isl.lionelmaquet.burger2home.CreditCard;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/creditcards")
    List<CreditCard> getAllCreditCards(){
        return creditCardService.getAllCreditCards();
    }

    @GetMapping("/creditcards/{creditCardIdentifier}")
    Optional<CreditCard> getSingleCreditCard(@PathVariable Integer creditCardIdentifier){
        return creditCardService.getCreditCard(creditCardIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/creditcards")
    List<CreditCard> getCreditCardsByUser(@PathVariable Integer userIdentifier){
        return creditCardService.getCreditCardsByUser(userIdentifier);
    }

    @PostMapping("/creditcards")
    CreditCard createCreditCard(@RequestBody CreditCardRequest creditCardRequest) throws StripeException {
        return creditCardService.createCreditCard(creditCardRequest.paymentMethodIdentifier, creditCardRequest.userId);
    }

    @PutMapping("/creditcards")
    CreditCard modifyCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.modifyCreditCard(creditCard);
    }

    @DeleteMapping("/creditcards/{creditCardIdentifier}")
    void deleteCreditCard(@PathVariable Integer creditCardIdentifier){
        creditCardService.deleteCreditCard(creditCardIdentifier);
    }

    private static class CreditCardRequest {
        String paymentMethodIdentifier;
        Integer userId;

        public String getPaymentMethodIdentifier() {
            return paymentMethodIdentifier;
        }

        public void setPaymentMethodIdentifier(String paymentMethodIdentifier) {
            this.paymentMethodIdentifier = paymentMethodIdentifier;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}

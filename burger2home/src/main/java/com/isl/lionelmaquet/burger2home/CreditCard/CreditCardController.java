package com.isl.lionelmaquet.burger2home.CreditCard;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class CreditCardController {

    @Autowired
    CreditCardService creditCardService;

    @GetMapping("/creditcards")
    @PreAuthorize("hasRole('ADMIN')")
    List<CreditCard> getAllCreditCards(@RequestParam(required = false, defaultValue = "false") Boolean mustBeActive){
        return mustBeActive ? creditCardService.getAllCreditCards().stream().filter(cc -> cc.getActive()).toList()
                : creditCardService.getAllCreditCards();
    }

    @GetMapping("/creditcards/{creditCardIdentifier}")
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("returnObject!= null ? returnObject.userId == authentication.principal.id or hasRole('ADMIN') : hasRole('ADMIN')")
    CreditCard getSingleCreditCard(@PathVariable Integer creditCardIdentifier){
        Optional<CreditCard> cc = creditCardService.getCreditCard(creditCardIdentifier);

        return cc.orElse(null);
    }

    @GetMapping("/users/{userIdentifier}/creditcards")
    @PreAuthorize("#userIdentifier == authentication.principal.id or hasRole('ADMIN')")
    List<CreditCard> getCreditCardsByUser(@PathVariable Integer userIdentifier, @RequestParam(required = false, defaultValue = "false") Boolean mustBeActive){
        return mustBeActive ? creditCardService.getCreditCardsByUser(userIdentifier).stream().filter(cc -> cc.getActive()).toList()
                : creditCardService.getCreditCardsByUser(userIdentifier);
    }

    @PostMapping("/creditcards")
    @PreAuthorize("hasRole('ADMIN') or #creditCardRequest.userId == authentication.principal.id")
    CreditCard createCreditCard(@RequestBody CreditCardRequest creditCardRequest) throws StripeException {
        return creditCardService.createCreditCard(creditCardRequest.paymentMethodIdentifier, creditCardRequest.userId);
    }

    @PutMapping("/creditcards")
    @PreAuthorize("hasRole('ADMIN') or #creditCard.userId == authentication.principal.id")
    CreditCard modifyCreditCard(@RequestBody CreditCard creditCard){
        return creditCardService.modifyCreditCard(creditCard);
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

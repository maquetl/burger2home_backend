package com.isl.lionelmaquet.burger2home.Keys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeysController {

    @GetMapping("/keys/stripe")
    public String getStripePublicKey(){
        return System.getenv(KEYS.STRIPE_PUBLIC_KEY.name());
    }

}

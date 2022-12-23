package com.isl.lionelmaquet.burger2home.Address;

import com.fasterxml.jackson.annotation.JsonView;
import com.sun.security.auth.UserPrincipal;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/addresses")
    List<Address> getAllAddresses(@RequestParam(required = false, defaultValue = "true") Boolean mustBeActive){
        return addressService.getAll(mustBeActive);
    }

    @GetMapping("/addresses/{addressIdentifier}")
    Address getSingleAddress(@PathVariable Integer addressIdentifier){
        Optional<Address> address = addressService.getSingleAddress(addressIdentifier);
        return address.isPresent() ? address.get() : null;
    }

    @GetMapping("/users/{userIdentifier}/addresses")
    List<Address> getAddressesByUser(@PathVariable Integer userIdentifier, @RequestParam(required = false, defaultValue = "true") Boolean mustBeActive){
        return addressService.getAddressesByUser(userIdentifier, mustBeActive);
    }

    @PostMapping("/addresses")
    Address createAddress(@RequestBody Address address){
        return addressService.createAddress(address);
    }

    @PutMapping("/addresses")
    Address modifySingleAddress(@RequestBody Address address){
        return addressService.modifyAddress(address);
    }
}

package com.isl.lionelmaquet.burger2home.Address;

import com.fasterxml.jackson.annotation.JsonView;
import com.isl.lionelmaquet.burger2home.Security.services.UserDetailsImpl;
import com.isl.lionelmaquet.burger2home.Utils.AuthUtils;
import com.sun.security.auth.UserPrincipal;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.resource.web.HeaderBearerTokenResolver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.security.RolesAllowed;
import java.net.HttpRetryException;
import java.util.*;

import static com.isl.lionelmaquet.burger2home.Utils.AuthUtils.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/addresses")
    @PreAuthorize(preauth_admin)
    List<Address> getAllAddresses(@RequestParam(required = false, defaultValue = "true") Boolean mustBeActive){
        return addressService.getAll(mustBeActive);
    }

    @GetMapping("/addresses/{addressIdentifier}")
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("returnObject != null ? returnObject.userId == authentication.principal.id or hasRole('ADMIN') : hasRole('ADMIN')")
    Address getSingleAddress(@PathVariable Integer addressIdentifier){
        Optional<Address> address = addressService.getSingleAddress(addressIdentifier);
        return address.isPresent() ? address.get() : null;
    }

    @PreAuthorize("#userIdentifier == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/users/{userIdentifier}/addresses")
    List<Address> getAddressesByUser(@PathVariable Integer userIdentifier, @RequestParam(required = false, defaultValue = "true") Boolean mustBeActive){
        return addressService.getAddressesByUser(userIdentifier, mustBeActive);
    }

    @PostMapping("/addresses")
    @PreAuthorize("#address.userId == authentication.principal.id or hasRole('ADMIN')")
    Address createAddress(@RequestBody Address address){
        return addressService.createAddress(address);
    }

    @PutMapping("/addresses")
    @PreAuthorize("#address.userId == authentication.principal.id or hasRole('ADMIN')")
    Address modifySingleAddress(@RequestBody Address address){
        return addressService.modifyAddress(address);
    }
}

package com.isl.lionelmaquet.burger2home.Address;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/addresses")
    List<Address> getAllAddresses(){
        return addressService.getAll();
    }

    @GetMapping("/addresses/{addressIdentifier}")
    Optional<Address> getSingleAddress(@PathVariable Integer addressIdentifier){
        return addressService.getSingleAddress(addressIdentifier);
    }

    @GetMapping("/users/{userIdentifier}/addresses")
    List<Address> getAddressesByUser(@PathVariable Integer userIdentifier){
        return addressService.getAddressesByUser(userIdentifier);
    }

    @PostMapping("/addresses")
    void createAddress(@RequestBody Address address){
        addressService.createAddress(address);
    }

    @DeleteMapping("/addresses/{addressIdentifier}")
    void deleteSingleAddress(@PathVariable Integer addressIdentifier){
        addressService.deleteAddressById(addressIdentifier);
    }

    @PutMapping("/addresses")
    void modifySingleAddress(@RequestBody Address address){
        addressService.modifyAddress(address);
    }






}

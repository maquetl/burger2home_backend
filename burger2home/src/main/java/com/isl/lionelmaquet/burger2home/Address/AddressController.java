package com.isl.lionelmaquet.burger2home.Address;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.service.spi.InjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

}

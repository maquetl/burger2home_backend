package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;


    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getJuprelleAddresses() {
        return addressRepository.findJuprelleAddresses();
    }
}

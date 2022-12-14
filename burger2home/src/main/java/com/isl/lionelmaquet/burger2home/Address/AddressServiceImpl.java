package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    AddressRepository addressRepository;


    @Override
    public List<Address> getAll(Boolean mustBeActive) {
        return mustBeActive ? addressRepository.findAll().stream().filter(a ->a.getActive()).toList() : addressRepository.findAll();
    }

    @Override
    public Optional<Address> getSingleAddress(Integer addressIdentifier) {
        return addressRepository.findById(addressIdentifier);
    }

    @Override
    public List<Address> getAddressesByUser(Integer userIdentifier, Boolean mustBeActive) {
        return mustBeActive ? addressRepository.findByUserId(userIdentifier).stream().filter(a ->a.getActive()).toList() : addressRepository.findByUserId(userIdentifier);
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddressById(Integer addressIdentifier) {
        addressRepository.deleteById(addressIdentifier);
    }

    @Override
    public Address modifyAddress(Address address) {
        return addressRepository.save(address);
    }

}

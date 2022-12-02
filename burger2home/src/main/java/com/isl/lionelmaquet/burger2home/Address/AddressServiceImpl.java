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
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public List<Address> getByUser(Integer userId) {
        return null;
    }

    @Override
    public Optional<Address> getSingleAddress(Integer addressIdentifier) {
        return addressRepository.findById(addressIdentifier);
    }

    @Override
    public List<Address> getAddressesByUser(Integer userIdentifier) {
        return addressRepository.findAddressesByUser(userIdentifier);
    }

    @Override
    public void createAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void deleteAddressById(Integer addressIdentifier) {
        addressRepository.deleteById(addressIdentifier);
    }

    @Override
    public void modifyAddress(Address address) {
        addressRepository.save(address);
    }

}

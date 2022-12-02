package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    public List<Address> getAll();
    public List<Address> getByUser(Integer userId);

    Optional<Address> getSingleAddress(Integer addressIdentifier);

    List<Address> getAddressesByUser(Integer userIdentifier);

    void createAddress(Address address);

    void deleteAddressById(Integer addressIdentifier);

    void modifyAddress(Address address);
}

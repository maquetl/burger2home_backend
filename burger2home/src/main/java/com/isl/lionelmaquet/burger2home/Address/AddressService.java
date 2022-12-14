package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> getAll(Boolean mustBeActive);

    Optional<Address> getSingleAddress(Integer addressIdentifier);

    List<Address> getAddressesByUser(Integer userIdentifier, Boolean mustBeActive);

    Address createAddress(Address address);

    void deleteAddressById(Integer addressIdentifier);

    Address modifyAddress(Address address);
}

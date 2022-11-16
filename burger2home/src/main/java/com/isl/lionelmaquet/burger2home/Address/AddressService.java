package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    public List<Address> getAll();
    public List<Address> getJuprelleAddresses();
}

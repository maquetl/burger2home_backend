package com.isl.lionelmaquet.burger2home.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE a.user.firstname LIKE 'Lionel'")
    List<Address> findJuprelleAddresses();
}
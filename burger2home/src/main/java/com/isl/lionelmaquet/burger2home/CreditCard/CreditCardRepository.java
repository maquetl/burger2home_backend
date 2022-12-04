package com.isl.lionelmaquet.burger2home.CreditCard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {
    List<CreditCard> findByUserId(Integer userIdentifier);
}
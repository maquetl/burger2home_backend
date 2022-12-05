package com.isl.lionelmaquet.burger2home.CreditCard;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    Optional<CreditCard> getCreditCard(Integer creditCardIdentifier);

    List<CreditCard> getCreditCardsByUser(Integer userIdentifier);

    void createCreditCard(CreditCard creditCard);

    void modifyCreditCard(CreditCard creditCard);

    void deleteCreditCard(Integer creditCardIdentifier);

    List<CreditCard> getAllCreditCards();
}

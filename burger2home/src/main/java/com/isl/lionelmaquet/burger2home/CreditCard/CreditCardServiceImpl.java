package com.isl.lionelmaquet.burger2home.CreditCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public Optional<CreditCard> getCreditCard(Integer creditCardIdentifier) {
        return creditCardRepository.findById(creditCardIdentifier);
    }

    @Override
    public List<CreditCard> getCreditCardsByUser(Integer userIdentifier) {
        return creditCardRepository.findByUserId(userIdentifier);
    }

    @Override
    public void createCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    @Override
    public void modifyCreditCard(CreditCard creditCard) {
        creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(Integer creditCardIdentifier) {
        creditCardRepository.deleteById(creditCardIdentifier);
    }


}

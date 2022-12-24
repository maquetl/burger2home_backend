package com.isl.lionelmaquet.burger2home.CreditCard;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface CreditCardService {
    Optional<CreditCard> getCreditCard(Integer creditCardIdentifier);

    List<CreditCard> getCreditCardsByUser(Integer userIdentifier);

    CreditCard modifyCreditCard(CreditCard creditCard);

    List<CreditCard> getAllCreditCards();

    Optional<CreditCard> getCreditCardByPaymentMethod(String paymentMethodId);

    CreditCard createCreditCard(String paymentMethodIdentifier, Integer userId) throws StripeException;

    CreditCard createCreditCard(CreditCard creditCard);

    CreditCard CreateCreditCardFromStripeCard(Integer userId, PaymentMethod paymentMethod);
}

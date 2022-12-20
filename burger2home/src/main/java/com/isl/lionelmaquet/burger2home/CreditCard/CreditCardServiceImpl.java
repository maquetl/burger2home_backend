package com.isl.lionelmaquet.burger2home.CreditCard;

import com.isl.lionelmaquet.burger2home.Keys.KEYS;
import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserService;
import com.isl.lionelmaquet.burger2home.Utils.StripeUtils;
import com.shippo.Shippo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    UserService userService;

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public Optional<CreditCard> getCreditCardByPaymentMethod(String paymentMethodId) {
        return creditCardRepository.findByPaymentMethodId(paymentMethodId);
    }

    static {
        Stripe.apiKey = System.getenv(KEYS.STRIPE_SECRET_KEY.name());
    }

    @Override
    public CreditCard createCreditCard(String paymentMethodIdentifier, Integer userId) throws StripeException {

        // Get or create customer based on user id
        Customer customer = StripeUtils.getCustomer(userId, userService);

        // Attach the customer to the payment method
        PaymentMethod paymentMethod = StripeUtils.attachPaymentMethodToCustomer(paymentMethodIdentifier, customer.getId());

        // Create the payment method in the db
        return CreateCreditCardFromStripeCard(userId, paymentMethod);
    }

    public CreditCard CreateCreditCardFromStripeCard(Integer userId, PaymentMethod paymentMethod) {
        PaymentMethod.Card card = (PaymentMethod.Card) paymentMethod.getCard();
        CreditCard creditCard;

        Optional<CreditCard> existingCC = creditCardRepository.findByPaymentMethodId(paymentMethod.getId());
        if(existingCC.isPresent()){
            creditCard = existingCC.get();
        } else {
            creditCard = new CreditCard();
        }

        creditCard.setBrand(card.getBrand());
        creditCard.setExpMonth(card.getExpMonth().toString());
        creditCard.setExpYear(card.getExpYear().toString());
        creditCard.setLast4(card.getLast4());
        creditCard.setPaymentMethodId(paymentMethod.getId());
        creditCard.setUserId(userId);
        return creditCardRepository.save(creditCard);
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
    public CreditCard createCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCard modifyCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    @Override
    public void deleteCreditCard(Integer creditCardIdentifier) {
        creditCardRepository.deleteById(creditCardIdentifier);
    }


}

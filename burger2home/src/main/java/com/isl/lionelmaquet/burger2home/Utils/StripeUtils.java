package com.isl.lionelmaquet.burger2home.Utils;

import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodAttachParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StripeUtils {

    public static Customer getCustomer(Integer userId, UserService userService) throws StripeException {
        User user = userService.getSingleUser(userId).get();
        String currentUserEmail = user.getEmail();
        Map<String, Object> options = new HashMap<>();
        options.put("email", currentUserEmail);
        List<Customer> customers = Customer.list(options).getData();

        Customer customer;
        if(customers.size() > 0){
            customer = customers.get(0);
        } else {
            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(user.getEmail())
                    .build();
            customer = Customer.create(params);
        }
        return customer;
    }

    public static PaymentMethod attachPaymentMethodToCustomer(String paymentMethodIdentifier, String customerId) throws StripeException {
        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodIdentifier);
        PaymentMethodAttachParams paymentMethodAttachParams = new PaymentMethodAttachParams.Builder().setCustomer(customerId).build();
        return paymentMethod.attach(paymentMethodAttachParams);
    }

    public static PaymentIntent confirmPaymentIntent(PaymentIntent paymentIntent, String paymentMethodIdentifier) throws StripeException {
        Map<String, Object> paymentIntentConfirmParams = new HashMap<>();
        paymentIntentConfirmParams.put("payment_method", paymentMethodIdentifier);
        return paymentIntent.confirm(paymentIntentConfirmParams);
    }
}

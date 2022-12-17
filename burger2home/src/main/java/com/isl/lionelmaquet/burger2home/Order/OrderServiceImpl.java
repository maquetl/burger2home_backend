package com.isl.lionelmaquet.burger2home.Order;

import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLineService;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLine;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLineService;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BasketService basketService;

    @Autowired
    BasketLineService basketLineService;

    @Autowired
    OrderLineService orderLineService;

    private PriceService priceService;

    @Autowired
    public void setPriceService(PriceService priceService){
        this.priceService = priceService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getSingleOrder(Integer orderIdentifier) {
        return orderRepository.findById(orderIdentifier);
    }

    @Override
    public List<Order> getOrdersByUser(Integer userIdentifier) {
        return orderRepository.findByUserId(userIdentifier);
    }

    @Override
    public Order createOrder(Integer basketIdentifier) throws StripeException {
        // Step 1 : Get basket
        Basket basket = basketService.getSingleBasket(basketIdentifier).get();

        // Step 2 : Create an order with status waiting for payment
        Order order = new Order();
        order.setUserId(basket.getUserId());
        order.setStatus(Order.Status.waiting_for_payment);
        order = orderRepository.save(order);

        // Step 3 : Create order lines based on basket lines. Add them to the order
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        for (BasketLine bl : basket.getBasketLines()){
            OrderLine ol = new OrderLine();
            ol.setOrderId(order.getId());
            ol.setProductId(bl.getProductId());
            ol.setAmount(bl.getAmount());
            orderLines.add(orderLineService.createOrderLine(ol));
        }

        order = orderRepository.findById(order.getId()).get();

        // Step 5 : Create a payment intent to stripe

        Float totalPrice = 0f;
        for(OrderLine ol : orderLines){
            totalPrice += priceService.getCurrentPriceAfterDiscountByProductId(ol.getProductId()) * ol.getAmount();
        }

        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (totalPrice*100))
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        order.setPaymentIntent(paymentIntent.getId());

        // Step 7 : Return the order
        return orderRepository.save(order);

    }

    @Override
    public void modifyOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Integer orderIdentifier) {
        orderRepository.deleteById(orderIdentifier);
    }

    @Override
    public Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException {
        Stripe.apiKey = "sk_test_51LkPo2INHgxPirwO5bJTiD8oC9OzRk0nebSXqgOk4BCafMWNDSWGwHZXmzwNuH7Pfp6OQ9wMaUxRv1czFWmdYaOz00d4jFRn4I";
        Order order = orderRepository.findById(orderIdentifier).get();

        String paymentIntentId = order.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", paymentMethodIdentifier);

        paymentIntent.confirm(params);

        order.setStatus(Order.Status.confirmed);

        return orderRepository.save(order);
    }
}

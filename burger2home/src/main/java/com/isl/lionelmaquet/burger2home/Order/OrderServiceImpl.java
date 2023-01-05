package com.isl.lionelmaquet.burger2home.Order;


import com.isl.lionelmaquet.burger2home.Address.Address;
import com.isl.lionelmaquet.burger2home.Address.AddressService;
import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Basket.BasketService;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLineService;
import com.isl.lionelmaquet.burger2home.CreditCard.CreditCard;
import com.isl.lionelmaquet.burger2home.CreditCard.CreditCardService;
import com.isl.lionelmaquet.burger2home.Keys.KEYS;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLine;
import com.isl.lionelmaquet.burger2home.OrderLine.OrderLineService;
import com.isl.lionelmaquet.burger2home.Price.Price;
import com.isl.lionelmaquet.burger2home.Price.PriceService;
import com.isl.lionelmaquet.burger2home.Promotion.Promotion;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import com.isl.lionelmaquet.burger2home.User.User;
import com.isl.lionelmaquet.burger2home.User.UserService;
import com.isl.lionelmaquet.burger2home.Utils.StripeUtils;
import com.shippo.Shippo;
import com.shippo.exception.APIConnectionException;
import com.shippo.exception.APIException;
import com.shippo.exception.AuthenticationException;
import com.shippo.exception.InvalidRequestException;
import com.shippo.model.Parcel;
import com.shippo.model.Shipment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.Instant;
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
    AddressService addressService;

    @Autowired
    OrderLineService orderLineService;

    @Autowired
    UserService userService;

    @Autowired
    CreditCardService creditCardService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private PromotionService promotionService;

    /**
     * PRINCIPE DE FONCTIONNEMENT DES COMMANDES
     *
     * On crée une commande sur basket d'un panier.
     * On donne donc un panier à la requête et sur base des basketLines, un order et des orderLines vont être créés.
     * Au moment de la création, chaque orderLine aura également une référence vers le prix et la promotion qui lui sont assignées.
     * Cela est uniquement fait par facilité et par soucis de tracabilité dans l'historization des commandes.
     *
     * Au moment de la confirmation de la commande, il y a une vérification qui est effectuée sur les prix et les promotions référénces dans les order lines.
     * En effet, si une promotion a prix fin, ou commencé, ou encore qu'un prix a changé depuis la création de la commande, il est important de mettre à jour le
     * paymentIntent dans Stripe, et d'informer l'utilisateur du nouveau prix.
     *
     */


    static {
        Stripe.apiKey = System.getenv(KEYS.STRIPE_SECRET_KEY.name());
        Shippo.apiKey = System.getenv(KEYS.SHIPPO_SECRET_KEY.name()); // this is the tesk token
    }

    @Override
    public List<Order> getAllOrders() throws StripeException {
        List<Order> orders = orderRepository.findAll();
        for(Order o : orders){
            updateOrderPricesAndPaymentIntent(o);
        }
        return orders;
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
    public Order createOrder(Integer basketIdentifier, Integer addressIdentifier) throws StripeException {

        // Retrieve the basket
        Basket basket = basketService.getSingleBasket(basketIdentifier).get();

        // Create a new order with a status "waiting for payment"
        Order order = createNewOrderFromBasket(basket, addressIdentifier);

        // Create order lines based on basket lines. Adds them to the order
        order.setOrderLines(new HashSet<>(createOrderLinesFromBasketLines(basket.getBasketLines().stream().toList(), order.getId())));

        // Get or create a stripe customer
        Customer customer = StripeUtils.getCustomer(basket.getUserId(), userService);

        // Create the payment intent to Stripe
        Float totalPrice = getOrderTotalPrice(order);
        PaymentIntent paymentIntent = createPaymentIntent(totalPrice, customer.getId());
        order.setPaymentIntent(paymentIntent.getId());

        // Save and return the order
        return orderRepository.save(order);
    }

    private Float getOrderTotalPrice(Order order) {

        Float totalPrice = 0f;

        // For each orderLine, calculate the price based on the price and the promotion.
        for(OrderLine ol : order.getOrderLines()){
            Float priceAmount = priceService.getSinglePrice(ol.getPriceId()).get().getAmount();
            Float promotionAmount = 0f;
            if (ol.getPromotionId() != null) promotionAmount = promotionService.getSinglePromotion(ol.getPromotionId()).get().getAmount();

            totalPrice += (priceAmount - (priceAmount * promotionAmount / 100)) * ol.getAmount();
        }
        return totalPrice;
    }

    @Override
    public Order modifyOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Integer orderIdentifier, String paymentMethodIdentifier) throws StripeException {
        Order order = orderRepository.findById(orderIdentifier).get();

        // A check is made here
        // If the promotion or price has changed since the order was created, we don't continue in the payment process
        // Instead, we now have an up-to-date order and Stripe payment intent, so we return the order to the client that can now confirm it.
        if(updateOrderPricesAndPaymentIntent(order)){
            return order;
        }

        // Retrieve the payment intent
        PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntent());

        // Attach the customer to the payment method
        // If the credit card was previously created, the stripe customer already has the payment method attached to it.
        // Attaching it again won't modify anything, so, there's no problem here.
        PaymentMethod paymentMethod = StripeUtils.attachPaymentMethodToCustomer(paymentMethodIdentifier, paymentIntent.getCustomer());

        // Create the payment method in the db
        creditCardService.CreateCreditCardFromStripeCard(order.getUserId(), paymentMethod);

        // Confirm the payment intent
        paymentIntent = StripeUtils.confirmPaymentIntent(paymentIntent, paymentMethodIdentifier);

        // Change the order status and the date and return it
        order.setStatus(Order.Status.payment_confirmed);
        order.setOrderDate(Instant.now());
        return orderRepository.save(order);
    }

    @Override
    public Order shipOrder(Integer orderIdentifier) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException {
        Order order = orderRepository.findById(orderIdentifier).get();
        Address address = addressService.getSingleAddress(order.getAddressId()).get();
        User user = userService.getSingleUser(order.getUserId()).get();

        // Create the TO address
        com.shippo.model.Address toAddress = createTOAddress(address, user);

        // Create the FROM address
        com.shippo.model.Address fromAddress = createFROMAddress();

        // Create the parcel
        Parcel createParcel = createParcel();

        // Create the shipment
        createShipment(fromAddress, toAddress, createParcel);

        // Change order status, save and return it
        order.setStatus(Order.Status.shipment_confirmed);
        return orderRepository.save(order);
    }

    private Order createNewOrderFromBasket(Basket basket, Integer addressIdentifier) {
        Order order = new Order();
        order.setUserId(basket.getUserId());
        order.setStatus(Order.Status.waiting_for_payment);
        order.setOrderDate(Instant.now());
        if(addressIdentifier != null) order.setAddressId(addressIdentifier);
        orderRepository.save(order);
        return order;
    }

    private PaymentIntent createPaymentIntent(Float totalPrice, String customerId) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (totalPrice *100))
                        .setCustomer(customerId)
                        .setCurrency("eur")
                        .addPaymentMethodType("card")
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent;
    }

    private List<OrderLine> createOrderLinesFromBasketLines(List<BasketLine> basketlines, Integer orderId) {
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        for (BasketLine bl : basketlines){
            OrderLine ol = new OrderLine();
            ol.setOrderId(orderId);
            ol.setProductId(bl.getProductId());
            ol.setAmount(bl.getAmount());
            ol.setPriceId(priceService.getCurrentPriceByProductId(bl.getProductId()).get().getId());
            Optional<Promotion> promo = promotionService.getCurrentPromotion(bl.getProductId());
            promo.ifPresent(p -> ol.setPromotionId(p.getId()));
            orderLines.add(orderLineService.createOrderLine(ol));
        }
        return orderLines;
    }

    public String getClientSecret(Integer orderIdentifier){
        Order order = orderRepository.findById(orderIdentifier).get();
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntent());
            return paymentIntent.getClientSecret();
        } catch (Exception e){

        }
        return "";
    }

    private void createShipment(com.shippo.model.Address fromAddress, com.shippo.model.Address toAddress, Parcel parcel) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> shipment = new HashMap<String, Object>();
        shipment.put("address_to", toAddress);
        shipment.put("address_from", fromAddress);
        shipment.put("parcels", parcel);
        Shipment createShipment = Shipment.create(shipment);
    }

    private Parcel createParcel() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> parcel = new HashMap<String, Object>();
        parcel.put("length", 10);
        parcel.put("width", 15);
        parcel.put("height", 10);
        parcel.put("distance_unit", "in");
        parcel.put("weight", 1);
        parcel.put("mass_unit", "lb");
        Parcel createParcel = Parcel.create(parcel);
        return createParcel;
    }


    // This method checks that the price and the promotion assigned to the orderlines at the moment of the order creation
    // are the same than the current prices and promotions.
    // If something has changed (a promotion has ended or started, a price has changed)
    // the updates are made to the order lines and to the payment intent.
    // It returns true if modifications were made to prices or promotions.
    private Boolean updateOrderPricesAndPaymentIntent(Order order) throws StripeException {

        // If an order was already paid, therefore, if the status is anything else that "waiting for payment", there is no need to check for new prices or updates.
        if(order.getStatus() != Order.Status.waiting_for_payment) return false;

        boolean priceHasChanged = false;
        boolean promoHasChanged = false;

        Float newTotalAmount = 0f;


        for(OrderLine ol : order.getOrderLines()){

            // We retrieve the CURRENT price and promotion
            Price currentPrice = priceService.getCurrentPriceByProductId(ol.getProductId()).get();
            Optional<Promotion> currentPromotion = promotionService.getCurrentPromotion(ol.getProductId());

            // We check that there is a promotion set to the orderline OR that a promotion is currently available
            // If it's not the case, we know that no updates should be made for the subject of promotions.
            if(currentPromotion.isPresent() || ol.getPromotionId() != null){
                if (currentPromotion.isPresent()){
                    // If the id set to the order (which could be null) is not the same that the current promotion id,
                    // we set the current promo to the order line and change the boolean variable to true
                    if (ol.getPromotionId() != currentPromotion.get().getId()){
                        ol.setPromotionId(currentPromotion.get().getId());
                        promoHasChanged = true;
                    }
                }

                // If there is no current promotion, we know that the order had a promotion set that is now finished.
                // therefore, we set it to null and update our boolean variable
                else {
                    ol.setPromotionId(null);
                    promoHasChanged = true;
                }
            }

            // If the id of the current price doesn't match the id of the price made at the moment of the order creation,
            // we update it and update our boolean variable
            if(currentPrice.getId() != ol.getPriceId()){
                ol.setPriceId(currentPrice.getId());
                priceHasChanged = true;
            }

            // We add to the new total amount the calculated current price of the order line
            newTotalAmount += currentPromotion.isPresent() ?
                    (currentPrice.getAmount() - (currentPrice.getAmount() * currentPromotion.get().getAmount() / 100) ) * ol.getAmount() :
                    currentPrice.getAmount() * ol.getAmount();
        }

        boolean paymentTotalHasChanged = false;

        // Here, if any of the prices or promo has changed, we know that we must update the payment intent
        if (priceHasChanged || promoHasChanged) {

            // We retrieve the payment intent
            PaymentIntent paymentIntent = PaymentIntent.retrieve(order.getPaymentIntent());

            // We update it only if the amount is different.
            // There is a case where the prices and promotions have changed but the total amount is still the same.
            // In that case, we don't have to update the payment intent
            // Example :
            //          Price of product 1 went from 10 to 12 (amount of 1)
            //          Price of product 2 went from 15 to 13 (amount of 1)
            //          old price == new price == 25
            if (paymentIntent.getAmount() != (newTotalAmount.longValue() * 100)){
                paymentTotalHasChanged = true;
                PaymentIntentUpdateParams paymentIntentUpdateParams = new PaymentIntentUpdateParams.Builder()
                        .setAmount(newTotalAmount.longValue() * 100)
                        .build();
                paymentIntent.update(paymentIntentUpdateParams);
            }
        }

        // We return the boolean indicating if the payment intent was changed.
        // If the total amount hasn't changed but that modifications were made to the prices and promo id
        // we can still continue in the payment process.
        // It only matters if the client has to pay a NEW price.
        return paymentTotalHasChanged;
    }

    private com.shippo.model.Address createFROMAddress() throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> fromAddressMap = new HashMap<String, Object>();
        fromAddressMap.put("name", "Lionel Maquet");
        fromAddressMap.put("street1", "Rue du tige 120");
        fromAddressMap.put("city", "Juprelle");
        fromAddressMap.put("zip", "4450");
        fromAddressMap.put("country", "BE");
        fromAddressMap.put("email", "lionel.maquet@gmail.com");
        com.shippo.model.Address createFromAddress = com.shippo.model.Address.create(fromAddressMap);
        return createFromAddress;
    }

    private com.shippo.model.Address createTOAddress(Address address, User user) throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
        Map<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put("name", user.getFirstname() + " " + user.getLastname());
        addressMap.put("street1", address.getStreet() + " " + address.getNumber());
        addressMap.put("city", address.getCity());
        addressMap.put("zip", address.getZipcode());
        addressMap.put("country", "BE");
        addressMap.put("email", user.getEmail());
        com.shippo.model.Address createAddress = com.shippo.model.Address.create(addressMap);
        return createAddress;
    }
}

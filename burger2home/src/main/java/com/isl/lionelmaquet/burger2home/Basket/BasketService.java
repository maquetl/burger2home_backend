package com.isl.lionelmaquet.burger2home.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketService {
    List<Basket> getAllBaksets();

    Optional<Basket> getSingleBasket(Integer basketIdentifier);

    Optional<Basket> getBasketByUser(Integer userIdentifier);

    Basket createBasket(Basket basket);

    Basket modifyBasket(Basket basket);
}

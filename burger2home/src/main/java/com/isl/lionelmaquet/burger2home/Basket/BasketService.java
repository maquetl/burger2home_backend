package com.isl.lionelmaquet.burger2home.Basket;

import java.util.List;
import java.util.Optional;

public interface BasketService {
    List<Basket> getAllBaksets();

    Optional<Basket> getSingleBasket(Integer basketIdentifier);

    Optional<Basket> getBasketByUser(Integer userIdentifier);

    void createBasket(Basket basket);

    void modifyBasket(Basket basket);

    void deleteBasket(Integer basketIdentifier);
}

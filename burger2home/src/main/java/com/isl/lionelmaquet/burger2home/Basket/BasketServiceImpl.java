package com.isl.lionelmaquet.burger2home.Basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    BasketRepository rep;

    @Override
    public List<Basket> getAllBaksets() {
        return rep.findAll();
    }

    @Override
    public Optional<Basket> getSingleBasket(Integer basketIdentifier) {
        return rep.findById(basketIdentifier);
    }

    @Override
    public Optional<Basket> getBasketByUser(Integer userIdentifier) {
        return rep.findByUserId(userIdentifier);
    }

    @Override
    public void createBasket(Basket basket) {
        rep.save(basket);
    }

    @Override
    public void modifyBasket(Basket basket) {
        rep.save(basket);
    }

    @Override
    public void deleteBasket(Integer basketIdentifier) {
        rep.deleteById(basketIdentifier);
    }
}

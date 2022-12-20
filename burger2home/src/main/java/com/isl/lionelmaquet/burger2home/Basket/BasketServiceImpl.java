package com.isl.lionelmaquet.burger2home.Basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    BasketRepository basketRepository;

    @Override
    public List<Basket> getAllBaksets() {
        return basketRepository.findAll();
    }

    @Override
    public Optional<Basket> getSingleBasket(Integer basketIdentifier) {
        return basketRepository.findById(basketIdentifier);
    }

    @Override
    public Optional<Basket> getBasketByUser(Integer userIdentifier) {
        return basketRepository.findByUserId(userIdentifier);
    }

    @Override
    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public Basket modifyBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    @Override
    public void deleteBasket(Integer basketIdentifier) {
        basketRepository.deleteById(basketIdentifier);
    }
}

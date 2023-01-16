package com.isl.lionelmaquet.burger2home.Basket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

        // Users can only have one basket
        getBasketByUser(basket.getUserId()).ifPresent(b -> {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);});

        return basketRepository.save(basket);
    }

    @Override
    public Basket modifyBasket(Basket basket) {

        Optional<Basket> bas = basketRepository.findById(basket.getId());
        if(bas.isPresent()){
            basket.setUserId(bas.get().getUserId());
            basketRepository.save(basket);
        }

        return bas.orElse(null);
    }
}

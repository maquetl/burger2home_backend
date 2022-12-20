package com.isl.lionelmaquet.burger2home.BasketLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketLineServiceImpl implements BasketLineService {

    @Autowired
    BasketLineRepository rep;

    @Override
    public List<BasketLine> getAllBasketLines() {
        return rep.findAll();
    }

    @Override
    public Optional<BasketLine> getSingleBasketLine(Integer basketLineIdentifier) {
        return rep.findById(basketLineIdentifier);
    }

    @Override
    public List<BasketLine> getBasketLinesByBasket(Integer basketIdentifier) {
        return rep.findByBasketId(Long.valueOf(basketIdentifier));
    }

    @Override
    public BasketLine createBasketLine(BasketLine basketLine) {
        return rep.save(basketLine);
    }

    @Override
    public BasketLine modifyBasketLine(BasketLine basketLine) {
        return rep.save(basketLine);
    }

    @Override
    public void deleteBasketLine(Integer basketLineIdentifier) {
        rep.deleteById(basketLineIdentifier);
    }
}

package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Override
    public Optional<Price> getCurrentPriceByProductId(Integer productId) {
        return Optional.ofNullable(priceRepository.findCurrentPriceByProductId(productId));
    }
}

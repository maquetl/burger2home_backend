package com.isl.lionelmaquet.burger2home.Price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Override
    public Price getCurrentPriceByProductId(Integer productId) {
        return priceRepository.findCurrentPriceByProductId(productId);
    }
}

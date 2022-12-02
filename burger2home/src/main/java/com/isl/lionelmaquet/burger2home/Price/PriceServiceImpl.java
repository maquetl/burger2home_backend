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

    @Override
    public List<Price> getAllPricesByProduct(Integer productIdentifier) {
        return priceRepository.findByProductId(productIdentifier);
    }

    @Override
    public List<Price> getAllPrices() {
        return priceRepository.findAll();
    }

    @Override
    public Optional<Price> getSinglePrice(Integer priceIdentifier) {
        return priceRepository.findById(priceIdentifier);
    }

    @Override
    public void createPrice(Price price) {
        priceRepository.save(price);
    }

    @Override
    public void modifyPrice(Price price) {
        priceRepository.save(price);
    }

    @Override
    public void deletePrice(Integer priceIdentifier) {
        priceRepository.deleteById(priceIdentifier);
    }
}

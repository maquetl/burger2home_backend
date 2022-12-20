package com.isl.lionelmaquet.burger2home.Price;

import com.isl.lionelmaquet.burger2home.Promotion.Promotion;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionRepository;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;

    @Autowired
    private PromotionService promotionService;

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

    public Price setCurrentPrice(Price price){
        Price newOldPrice = priceRepository.findCurrentPriceByProductId(price.getProductId());

        Price newCurrentPrice = new Price();
        newCurrentPrice.setStartDate(LocalDate.now());
        newCurrentPrice.setEndDate(newOldPrice.getEndDate());
        newCurrentPrice.setAmount(price.getAmount());
        newCurrentPrice.setProductId(price.getProductId());
        priceRepository.save(newCurrentPrice);

        newOldPrice.setEndDate(LocalDate.now());
        priceRepository.save(newOldPrice);

        return newCurrentPrice;
    }

    public Price setNextPrice(Price price){

        Price currentPrice = priceRepository.findCurrentPriceByProductId(price.getProductId());
        currentPrice.setEndDate(price.getStartDate());
        priceRepository.save(currentPrice);

        Optional<Price> nextPrice = priceRepository.findNextPrice(price.getProductId());
        if(nextPrice.isPresent()){
            nextPrice.get().setAmount(price.getAmount());
            nextPrice.get().setStartDate(price.getStartDate());
            return priceRepository.save(nextPrice.get());
        }

        Price newNextPrice = new Price();
        newNextPrice.setProductId(price.getProductId());
        newNextPrice.setStartDate(price.getStartDate());
        newNextPrice.setAmount(price.getAmount());
        return priceRepository.save(newNextPrice);
    }

    public Price createDefaultCurrentPrice(Integer productId){
        Price price = new Price();
        price.setProductId(productId);
        price.setStartDate(LocalDate.now());
        price.setAmount(0f);
        return priceRepository.save(price);
    }

    public Optional<Price> getNextPrice(Integer productId){
        return priceRepository.findNextPrice(productId);
    }

    @Override
    public void createPrice(Price price) {
        priceRepository.save(price);
    }

    @Override
    public Float getCurrentPriceAfterDiscountByProductId(Integer productId) {
        Optional<Price> price = getCurrentPriceByProductId(productId);
        Optional<Promotion> promo = promotionService.getCurrentPromotion(productId);

        if (promo.isPresent()){
            return price.get().getAmount() - (price.get().getAmount() * (promo.get().getAmount() / 100));
        }

        return price.get().getAmount();

    }
}

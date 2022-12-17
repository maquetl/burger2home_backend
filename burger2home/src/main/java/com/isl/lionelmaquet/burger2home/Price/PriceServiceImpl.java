package com.isl.lionelmaquet.burger2home.Price;

import com.isl.lionelmaquet.burger2home.Promotion.Promotion;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionRepository;
import com.isl.lionelmaquet.burger2home.Promotion.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    PriceRepository priceRepository;

    private PromotionService promotionService;

    @Autowired
    public void setPromotionService(PromotionService promotionService){
        this.promotionService = promotionService;
    }

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

package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public Optional<Promotion> getCurrentPromotion(Product product) {
        return promotionRepository.findCurrentPromotion(product);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> getSinglePromotion(Integer promotionIdentifier) {
        return promotionRepository.findById(promotionIdentifier);
    }

    @Override
    public void createSinglePromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public void modifySinglePromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Override
    public void deleteSinglePromotion(Integer promotionIdentifier) {
        promotionRepository.deleteById(promotionIdentifier);
    }
}

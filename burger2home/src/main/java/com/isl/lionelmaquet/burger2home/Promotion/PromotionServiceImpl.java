package com.isl.lionelmaquet.burger2home.Promotion;

import com.isl.lionelmaquet.burger2home.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public Optional<Promotion> getCurrentPromotion(Product product) {
        return promotionRepository.findCurrentPromotion(product);
    }
}

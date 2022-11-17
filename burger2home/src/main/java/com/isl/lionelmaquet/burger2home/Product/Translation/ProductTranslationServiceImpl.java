package com.isl.lionelmaquet.burger2home.Product.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ProductTranslationServiceImpl implements ProductTranslationService {

    @Autowired
    ProductTranslationRepository productTranslationRepository;

    @Override
    public ProductTranslation getByProductAndLanguage(Integer productId, String languageAbbr) {
        return productTranslationRepository.findByProductAndLanguage(productId, languageAbbr);
    }
}

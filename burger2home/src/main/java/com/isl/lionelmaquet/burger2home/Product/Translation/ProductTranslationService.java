package com.isl.lionelmaquet.burger2home.Product.Translation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ProductTranslationService {
    Optional<ProductTranslation> getByProductAndLanguage(Integer productId, String languageAbbr);
    List<ProductTranslation> getByProduct(Integer productId);
    void createProductTranslation(ProductTranslation productTranslation);
    void deleteByProductId(Integer productId);
    void deleteById(Integer productTranslationId);
    void modifyProductTranslation(ProductTranslation productTranslation);
}

package com.isl.lionelmaquet.burger2home.Product.Translation;

import org.springframework.stereotype.Service;


public interface ProductTranslationService {
    ProductTranslation getByProductAndLanguage(Integer productId, String languageAbbr);
}

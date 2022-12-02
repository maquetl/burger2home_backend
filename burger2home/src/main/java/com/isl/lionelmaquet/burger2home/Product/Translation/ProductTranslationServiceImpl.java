package com.isl.lionelmaquet.burger2home.Product.Translation;

import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.Language.LanguageRepository;
import com.isl.lionelmaquet.burger2home.Language.LanguageService;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.Product.ProductRepository;
import com.isl.lionelmaquet.burger2home.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductTranslationServiceImpl implements ProductTranslationService {

    @Autowired
    ProductTranslationRepository productTranslationRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public Optional<ProductTranslation> getByProductAndLanguage(Integer productId, String languageAbbr) {
        ProductTranslation pt = productTranslationRepository.findByProductAndLanguage(productId, languageAbbr);
        return Optional.ofNullable(pt);
    }

    @Override
    public List<ProductTranslation> getByProduct(Integer productId) {
        return productTranslationRepository.findByProductId(productId);
    }

    @Override
    public void createProductTranslation(ProductTranslation productTranslation) {
        productTranslationRepository.save(productTranslation);
    }

    @Override
    public void deleteByProductId(Integer productId) {
        for(ProductTranslation pt : getByProduct(productId)){
            productRepository.deleteById(pt.getId());
        }
    }

    @Override
    public void deleteById(Integer productTranslationId) {
        productTranslationRepository.deleteById(productTranslationId);
    }

    @Override
    public void modifyProductTranslation(ProductTranslation productTranslation) {
        productTranslationRepository.save(productTranslation);
    }

}

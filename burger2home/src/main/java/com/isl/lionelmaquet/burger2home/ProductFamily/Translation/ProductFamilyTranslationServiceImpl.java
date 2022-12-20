package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductFamilyTranslationServiceImpl implements ProductFamilyTranslationService {

    @Autowired
    ProductFamilyTranslationRepository productFamilyTranslationRepository;

    @Override
    public List<ProductFamilyTranslation> getAllProductFamilyTranslations() {
        return productFamilyTranslationRepository.findAll();
    }

    @Override
    public Optional<ProductFamilyTranslation> getSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier) {
        return productFamilyTranslationRepository.findById(productFamilyTranslationIdentifier);
    }

    @Override
    public List<ProductFamilyTranslation> getProductFamilyTranslationsByProductFamily(Integer productFamilyIdentifier) {
        return productFamilyTranslationRepository.findByProductFamilyId(productFamilyIdentifier);
    }

    @Override
    public ProductFamilyTranslation createProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation) {
        return productFamilyTranslationRepository.save(productFamilyTranslation);
    }

    @Override
    public ProductFamilyTranslation modifyProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation) {
        return productFamilyTranslationRepository.save(productFamilyTranslation);
    }

    @Override
    public void deleteSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier) {
        productFamilyTranslationRepository.deleteById(productFamilyTranslationIdentifier);
    }
}

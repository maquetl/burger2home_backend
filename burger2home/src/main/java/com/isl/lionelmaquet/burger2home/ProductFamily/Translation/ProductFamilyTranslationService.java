package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import java.util.List;
import java.util.Optional;

public interface ProductFamilyTranslationService {
    List<ProductFamilyTranslation> getAllProductFamilyTranslations();

    Optional<ProductFamilyTranslation> getSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier);

    List<ProductFamilyTranslation> getProductFamilyTranslationsByProductFamily(Integer productFamilyIdentifier);

    void createProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation);

    void modifyProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation);

    void deleteSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier);
}

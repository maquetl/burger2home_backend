package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import java.util.List;
import java.util.Optional;

public interface ProductFamilyTranslationService {
    List<ProductFamilyTranslation> getAllProductFamilyTranslations();

    Optional<ProductFamilyTranslation> getSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier);

    List<ProductFamilyTranslation> getProductFamilyTranslationsByProductFamily(Integer productFamilyIdentifier);

    ProductFamilyTranslation createProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation);

    ProductFamilyTranslation modifyProductFamilyTranslation(ProductFamilyTranslation productFamilyTranslation);

    void deleteSingleProductFamilyTranslation(Integer productFamilyTranslationIdentifier);
}

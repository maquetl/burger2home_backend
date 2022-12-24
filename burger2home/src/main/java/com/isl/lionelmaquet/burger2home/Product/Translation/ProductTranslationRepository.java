package com.isl.lionelmaquet.burger2home.Product.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Integer> {

    @Query("SELECT p FROM ProductTranslation p WHERE p.productId = ?1 AND p.language.abbreviation = ?2")
    ProductTranslation findByProductAndLanguage(Integer productId, String languageAbbr);

    List<ProductTranslation> findByProductId(Integer productId);

    List<ProductTranslation> findByLanguageId(Integer languageId);
}
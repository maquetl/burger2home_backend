package com.isl.lionelmaquet.burger2home.Product.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTranslationRepository extends JpaRepository<ProductTranslation, Integer> {

    @Query("SELECT p FROM ProductTranslation p WHERE p.product.id = ?1 AND p.language.abbreviation = ?2")
    ProductTranslation findByProductAndLanguage(Integer productId, String languageAbbr);

}
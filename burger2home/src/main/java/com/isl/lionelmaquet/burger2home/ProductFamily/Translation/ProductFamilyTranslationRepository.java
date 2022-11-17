package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFamilyTranslationRepository extends JpaRepository<ProductFamilyTranslation, Integer> {
}
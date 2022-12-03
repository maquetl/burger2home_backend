package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductFamilyTranslationRepository extends JpaRepository<ProductFamilyTranslation, Integer> {

    List<ProductFamilyTranslation> findByProductFamilyId(Integer productFamilyIdentifier);
}
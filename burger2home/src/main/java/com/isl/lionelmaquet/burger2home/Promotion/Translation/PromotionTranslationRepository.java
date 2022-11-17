package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionTranslationRepository extends JpaRepository<PromotionTranslation, Integer> {
}
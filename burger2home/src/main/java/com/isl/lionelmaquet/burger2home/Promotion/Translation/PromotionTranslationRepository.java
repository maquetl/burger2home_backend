package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionTranslationRepository extends JpaRepository<PromotionTranslation, Integer> {
    List<PromotionTranslation> findByPromotionId(Integer promotionId);
}
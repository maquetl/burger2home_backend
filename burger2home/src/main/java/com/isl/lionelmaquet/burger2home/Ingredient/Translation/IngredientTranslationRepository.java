package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTranslationRepository extends JpaRepository<IngredientTranslation, Integer> {
}
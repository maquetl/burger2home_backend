package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientTranslationRepository extends JpaRepository<IngredientTranslation, Integer> {
    List<IngredientTranslation> findByIngredientId(Integer ingredientIdentifier);
}
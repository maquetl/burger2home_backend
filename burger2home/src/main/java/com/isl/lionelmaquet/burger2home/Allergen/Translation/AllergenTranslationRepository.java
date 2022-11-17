package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergenTranslationRepository extends JpaRepository<AllergenTranslation, Integer> {
}
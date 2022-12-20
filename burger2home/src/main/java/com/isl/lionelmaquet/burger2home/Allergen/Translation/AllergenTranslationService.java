package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import java.util.List;
import java.util.Optional;

public interface AllergenTranslationService {
    List<AllergenTranslation> getAllAllergenTranslations();

    Optional<AllergenTranslation> getSingleAllergenTranslation(Integer allergenTranslationIdentifier);

    List<AllergenTranslation> getAllergenTranslationByAllergen(Integer allergenIdentifier);

    AllergenTranslation createAllergenTranslation(AllergenTranslation allergenTranslation);

    AllergenTranslation modifyAllergenTranslation(AllergenTranslation allergenTranslation);

    void deleteAllergenTranslation(Integer allergenTranslationIdentifier);
}

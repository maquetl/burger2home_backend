package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllergenTranslationServiceImpl implements AllergenTranslationService {

    @Autowired
    AllergenTranslationRepository allergenTranslationRepository;


    @Override
    public List<AllergenTranslation> getAllAllergenTranslations() {
        return allergenTranslationRepository.findAll();
    }

    @Override
    public List<AllergenTranslation> getByLanguage(Integer languageId) {
        return allergenTranslationRepository.findByLanguageId(languageId);
    }

    @Override
    public Optional<AllergenTranslation> getSingleAllergenTranslation(Integer allergenTranslationIdentifier) {
        return allergenTranslationRepository.findById(allergenTranslationIdentifier);
    }

    @Override
    public List<AllergenTranslation> getAllergenTranslationByAllergen(Integer allergenIdentifier) {
        return allergenTranslationRepository.findByAllergenId(allergenIdentifier);
    }

    @Override
    public AllergenTranslation createAllergenTranslation(AllergenTranslation allergenTranslation) {
        return allergenTranslationRepository.save(allergenTranslation);
    }

    @Override
    public AllergenTranslation modifyAllergenTranslation(AllergenTranslation allergenTranslation) {
        return allergenTranslationRepository.save(allergenTranslation);
    }

    @Override
    public void deleteAllergenTranslation(Integer allergenTranslationIdentifier) {
        allergenTranslationRepository.deleteById(allergenTranslationIdentifier);
    }
}

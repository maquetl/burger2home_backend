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
    public Optional<AllergenTranslation> getSingleAllergenTranslation(Integer allergenTranslationIdentifier) {
        return allergenTranslationRepository.findById(allergenTranslationIdentifier);
    }

    @Override
    public List<AllergenTranslation> getAllergenTranslationByAllergen(Integer allergenIdentifier) {
        return allergenTranslationRepository.findByAllergenId(allergenIdentifier);
    }

    @Override
    public void createAllergenTranslation(AllergenTranslation allergenTranslation) {
        allergenTranslationRepository.save(allergenTranslation);
    }

    @Override
    public void modifyAllergenTranslation(AllergenTranslation allergenTranslation) {
        allergenTranslationRepository.save(allergenTranslation);
    }

    @Override
    public void deleteAllergenTranslation(Integer allergenTranslationIdentifier) {
        allergenTranslationRepository.deleteById(allergenTranslationIdentifier);
    }
}

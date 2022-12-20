package com.isl.lionelmaquet.burger2home.Language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> getSingleLanguage(Integer languageIdentifier) {
        return languageRepository.findById(languageIdentifier);
    }

    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language modifyLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(Integer languageIdentifier) {
        languageRepository.deleteById(languageIdentifier);
    }
}

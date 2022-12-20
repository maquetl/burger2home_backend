package com.isl.lionelmaquet.burger2home.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    List<Language> getAllLanguages();

    Optional<Language> getSingleLanguage(Integer languageIdentifier);

    Language createLanguage(Language language);

    Language modifyLanguage(Language language);

    void deleteLanguage(Integer languageIdentifier);
}

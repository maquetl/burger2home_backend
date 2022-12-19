package com.isl.lionelmaquet.burger2home.Utils;

import com.isl.lionelmaquet.burger2home.Translation;

import java.util.List;

public class TranslationUtils {
    public static boolean checkLanguage(String languageAbrr, Translation translation){
        return translation.getLanguage().getAbbreviation().equals(languageAbrr);
    }

    public static List<? extends Translation> filterTranslationsByLanguage(List<? extends Translation> translations, String languageAbbr){
        if (languageAbbr == null) return translations;
        return translations.stream().filter(t -> checkLanguage(languageAbbr, t)).toList();
    }
}

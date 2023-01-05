package com.isl.lionelmaquet.burger2home.Type.Translation;

import java.util.List;

public interface TypeTranslationService {
    List<TypeTranslation> getByType(Integer typeIdentifier);

    TypeTranslation create(TypeTranslation typeTranslation);

    TypeTranslation modify(TypeTranslation typeTranslation);

    void deleteById(Integer translationId);

    List<TypeTranslation> getAll();

    TypeTranslation getSingle(Integer typeTranslationIdentifier);
}

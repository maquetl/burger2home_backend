package com.isl.lionelmaquet.burger2home.Type.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeTranslationServiceImpl implements TypeTranslationService {

    @Autowired
    TypeTranslationRepository typeTranslationRepository;

    @Override
    public List<TypeTranslation> getByType(Integer typeIdentifier) {
        return typeTranslationRepository.findByTypeId(typeIdentifier);
    }

    @Override
    public TypeTranslation create(TypeTranslation typeTranslation) {
        return typeTranslationRepository.save(typeTranslation);
    }

    @Override
    public TypeTranslation modify(TypeTranslation typeTranslation) {
        return typeTranslationRepository.save(typeTranslation);
    }

    @Override
    public void deleteById(Integer translationId) {
        typeTranslationRepository.deleteById(translationId);
    }

    @Override
    public List<TypeTranslation> getAll() {
        return typeTranslationRepository.findAll();
    }

    @Override
    public TypeTranslation getSingle(Integer typeTranslationIdentifier) {
        return typeTranslationRepository.findById(typeTranslationIdentifier).get();
    }
}

package com.isl.lionelmaquet.burger2home.Type.Translation;

import com.isl.lionelmaquet.burger2home.Type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeTranslationRepository extends JpaRepository<TypeTranslation, Integer> {
    List<TypeTranslation> findByTypeId(Integer typeIdentifier);

    List<TypeTranslation> findByLanguageId(Integer languageId);
}

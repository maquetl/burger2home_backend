package com.isl.lionelmaquet.burger2home.Type.Translation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.Translation;
import com.isl.lionelmaquet.burger2home.Type.Type;

import javax.persistence.*;

@Entity
@Table(name = "type_translation")
@JsonSerialize
public class TypeTranslation implements Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type_id")
    private Integer typeId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
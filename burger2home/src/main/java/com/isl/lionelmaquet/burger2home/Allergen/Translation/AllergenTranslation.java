package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.Translation;

import javax.persistence.*;

@Entity
@Table(name = "allergen_translation")
@JsonSerialize
public class AllergenTranslation implements Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;


    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "allergen_id")
    private Integer allergenId;

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getAllergenId() {
        return allergenId;
    }

    public void setAllergenId(Integer allergenId) {
        this.allergenId = allergenId;
    }

}
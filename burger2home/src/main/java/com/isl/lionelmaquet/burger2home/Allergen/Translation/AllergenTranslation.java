package com.isl.lionelmaquet.burger2home.Allergen.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Language.Language;

import javax.persistence.*;

@Entity
@Table(name = "allergen_translation")
public class AllergenTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "allergen_id", nullable = false)
    @JsonIgnore
    private Allergen allergen;

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

    public Allergen getAllergen() {
        return allergen;
    }

    public void setAllergen(Allergen allergen) {
        this.allergen = allergen;
    }

}
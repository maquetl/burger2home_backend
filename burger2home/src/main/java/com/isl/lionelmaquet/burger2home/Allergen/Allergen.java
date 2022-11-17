package com.isl.lionelmaquet.burger2home.Allergen;

import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "allergen")
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "allergen")
    private Set<AllergenTranslation> allergenTranslations = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<AllergenTranslation> getAllergenTranslations() {
        return allergenTranslations;
    }

    public void setAllergenTranslations(Set<AllergenTranslation> allergenTranslations) {
        this.allergenTranslations = allergenTranslations;
    }

}
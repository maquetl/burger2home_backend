package com.isl.lionelmaquet.burger2home.Allergen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Allergen.Translation.AllergenTranslation;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "allergen")
@JsonSerialize
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "allergen")
    @JsonIgnore
    private Set<AllergenTranslation> allergenTranslations = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "allergens")
    @JsonIgnore
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


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
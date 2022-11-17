package com.isl.lionelmaquet.burger2home.Ingredient;

import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientTranslation> ingredientTranslations = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<IngredientTranslation> getIngredientTranslations() {
        return ingredientTranslations;
    }

    public void setIngredientTranslations(Set<IngredientTranslation> ingredientTranslations) {
        this.ingredientTranslations = ingredientTranslations;
    }

}
package com.isl.lionelmaquet.burger2home.Ingredient.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Language.Language;

import javax.persistence.*;

@Entity
@Table(name = "ingredient_translation")
@JsonSerialize
public class IngredientTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    @JsonIgnore
    private Ingredient ingredient;

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

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

}
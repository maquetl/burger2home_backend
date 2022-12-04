package com.isl.lionelmaquet.burger2home.Ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Product.Product;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ingredient")
@JsonSerialize
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "ingredientId")
    @JsonIgnore
    private Set<IngredientTranslation> ingredientTranslations = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "allergen_ingredient",
            joinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id", referencedColumnName = "id"))
    private Set<Allergen> allergens = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private Set<Product> products = new LinkedHashSet<>();

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<Allergen> allergens) {
        this.allergens = allergens;
    }

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
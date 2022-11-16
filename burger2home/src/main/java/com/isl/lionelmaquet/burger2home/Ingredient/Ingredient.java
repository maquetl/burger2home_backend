package com.isl.lionelmaquet.burger2home.Ingredient;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Ingredient.Translation.IngredientTranslation;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.StockHistorization.StockHistorization;

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
    private Set<StockHistorization> stockHistorizations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ingredient")
    private Set<IngredientTranslation> ingredientTranslations = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "allergen_ingredient",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id"))
    private Set<Allergen> allergens = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "product_ingredient",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<StockHistorization> getStockHistorizations() {
        return stockHistorizations;
    }

    public void setStockHistorizations(Set<StockHistorization> stockHistorizations) {
        this.stockHistorizations = stockHistorizations;
    }

    public Set<IngredientTranslation> getIngredientTranslations() {
        return ingredientTranslations;
    }

    public void setIngredientTranslations(Set<IngredientTranslation> ingredientTranslations) {
        this.ingredientTranslations = ingredientTranslations;
    }

    public Set<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<Allergen> allergens) {
        this.allergens = allergens;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
package com.isl.lionelmaquet.burger2home.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Product.Translation.ProductTranslation;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@JsonSerialize
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

    @OneToMany(mappedBy = "productId", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<ProductTranslation> productTranslations = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_family_product",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_family_id", referencedColumnName = "id"))
    private Set<ProductFamily> productFamilies = new LinkedHashSet<>();

    public Set<ProductFamily> getProductFamilies() {
        return productFamilies;
    }

    public void setProductFamilies(Set<ProductFamily> productFamilies) {
        this.productFamilies = productFamilies;
    }

    public Set<ProductTranslation> getProductTranslations() {
        return productTranslations;
    }

    public void setProductTranslations(Set<ProductTranslation> productTranslations) {
        this.productTranslations = productTranslations;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import com.isl.lionelmaquet.burger2home.Promotion.Promotion;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany
    @JoinTable(name = "promotion_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    private Set<Promotion> promotions = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "product_family_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_family_id"))
    private Set<ProductFamily> productFamilies = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new LinkedHashSet<>();

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

    public Set<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(Set<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Set<ProductFamily> getProductFamilies() {
        return productFamilies;
    }

    public void setProductFamilies(Set<ProductFamily> productFamilies) {
        this.productFamilies = productFamilies;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
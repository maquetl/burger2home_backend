package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Price.Price;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductBO {
    private Integer id;
    private String name;
    private String description;
    private Float currentPrice;
    private Float currentDiscount;
    private Float actualPrice;
    private String imageUrl;
    private Boolean isAvailable;
    private List<String> ingredients = new ArrayList<>();
    private List<String> allergens = new ArrayList<>();
    private List<Integer> productFamilies = new ArrayList<>();

    public ProductBO() {
    }

    public List<Integer> getProductFamilies() {
        return productFamilies;
    }

    public void setProductFamilies(List<Integer> productFamilies) {
        this.productFamilies = productFamilies;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Float getCurrentDiscount() {
        return currentDiscount;
    }

    public void setCurrentDiscount(Float currentDiscount) {
        this.currentDiscount = currentDiscount;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Float getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Float actualPrice) {
        this.actualPrice = actualPrice;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<String> allergens) {
        this.allergens = allergens;
    }
}

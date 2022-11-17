package com.isl.lionelmaquet.burger2home.Product;

import com.isl.lionelmaquet.burger2home.Allergen.Allergen;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;
import com.isl.lionelmaquet.burger2home.Price.Price;

import java.util.List;

public class ProductBO {
    private Integer id;
    private String name;
    private String description;
    private Float currentPrice;
    private Float currentDiscount;
    private String imageUrl;
    private Boolean isAvailable;
    private List<Ingredient> ingredients;
    private List<Allergen> allergens;

    public ProductBO() {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }
}

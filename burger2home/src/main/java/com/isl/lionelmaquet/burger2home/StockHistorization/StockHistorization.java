package com.isl.lionelmaquet.burger2home.StockHistorization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isl.lionelmaquet.burger2home.Ingredient.Ingredient;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stock_historization")
public class StockHistorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

}
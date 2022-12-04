package com.isl.lionelmaquet.burger2home.Promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.Promotion.Translation.PromotionTranslation;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant creationDate;

    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant endDate;

    @ManyToMany
    @JoinTable(name = "promotion_product",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(mappedBy = "promotionId")
    @JsonIgnore
    private Set<PromotionTranslation> promotionTranslations = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<PromotionTranslation> getPromotionTranslations() {
        return promotionTranslations;
    }

    public void setPromotionTranslations(Set<PromotionTranslation> promotionTranslations) {
        this.promotionTranslations = promotionTranslations;
    }

}
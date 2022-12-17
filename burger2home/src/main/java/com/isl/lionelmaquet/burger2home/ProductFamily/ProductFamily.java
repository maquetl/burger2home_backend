package com.isl.lionelmaquet.burger2home.ProductFamily;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isl.lionelmaquet.burger2home.Product.Product;
import com.isl.lionelmaquet.burger2home.ProductFamily.Translation.ProductFamilyTranslation;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "product_family")
public class ProductFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "productFamilyId")
    @JsonIgnore
    private Set<ProductFamilyTranslation> productFamilyTranslations = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "productFamilies")
    @JsonIgnore
    private Set<Product> products = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ProductFamilyTranslation> getProductFamilyTranslations() {
        return productFamilyTranslations;
    }

    public void setProductFamilyTranslations(Set<ProductFamilyTranslation> productFamilyTranslations) {
        this.productFamilyTranslations = productFamilyTranslations;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

}
package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;
import com.isl.lionelmaquet.burger2home.Translation;

import javax.persistence.*;

@Entity
@Table(name = "product_family_translation")
public class ProductFamilyTranslation implements Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "product_family_id")
    private Integer productFamilyId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "name", length = 60)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductFamilyId() {
        return productFamilyId;
    }

    public void setProductFamilyId(Integer productFamilyId) {
        this.productFamilyId = productFamilyId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
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

}
package com.isl.lionelmaquet.burger2home.ProductFamily.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.ProductFamily.ProductFamily;

import javax.persistence.*;

@Entity
@Table(name = "product_family_translation")
public class ProductFamilyTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_family_id", nullable = false)
    @JsonIgnore
    private ProductFamily productFamily;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    public ProductFamily getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(ProductFamily productFamily) {
        this.productFamily = productFamily;
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
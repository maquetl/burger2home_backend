package com.isl.lionelmaquet.burger2home.Promotion.Translation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isl.lionelmaquet.burger2home.Language.Language;
import com.isl.lionelmaquet.burger2home.Promotion.Promotion;

import javax.persistence.*;

@Entity
@Table(name = "promotion_translation")
public class PromotionTranslation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_id", nullable = false)
    @JsonIgnore
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
package com.isl.lionelmaquet.burger2home.Type;

import com.isl.lionelmaquet.burger2home.Type.Translation.TypeTranslation;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "type")
    private Set<TypeTranslation> typeTranslations = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<TypeTranslation> getTypeTranslations() {
        return typeTranslations;
    }

    public void setTypeTranslations(Set<TypeTranslation> typeTranslations) {
        this.typeTranslations = typeTranslations;
    }

}
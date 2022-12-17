package com.isl.lionelmaquet.burger2home.Basket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isl.lionelmaquet.burger2home.BasketLine.BasketLine;
import com.isl.lionelmaquet.burger2home.User.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "last_update")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant lastUpdate;

    @Column(name = "user_id")
    private Integer userId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "basketId")
    private Set<BasketLine> basketLines = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<BasketLine> getBasketLines() {
        return basketLines;
    }

    public void setBasketLines(Set<BasketLine> basketLines) {
        this.basketLines = basketLines;
    }

}
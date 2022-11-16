package com.isl.lionelmaquet.burger2home.BasketLine;

import com.isl.lionelmaquet.burger2home.Basket.Basket;

import javax.persistence.*;

@Entity
@Table(name = "basket_line")
public class BasketLine {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
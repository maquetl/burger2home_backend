package com.isl.lionelmaquet.burger2home.BasketLine;

import com.isl.lionelmaquet.burger2home.Basket.Basket;
import com.isl.lionelmaquet.burger2home.Product.Product;

import javax.persistence.*;

@Entity
@Table(name = "basket_line")
public class BasketLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "basket_id")
    private Long basketId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
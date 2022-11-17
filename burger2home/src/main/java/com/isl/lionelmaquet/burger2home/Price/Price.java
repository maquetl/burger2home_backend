package com.isl.lionelmaquet.burger2home.Price;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isl.lionelmaquet.burger2home.Product.Product;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "start_date")
    private Instant startDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

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

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
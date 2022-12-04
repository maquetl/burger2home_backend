package com.isl.lionelmaquet.burger2home.CreditCard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.isl.lionelmaquet.burger2home.User.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credit_card")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "holder_name", nullable = false, length = 45)
    private String holderName;

    @Column(name = "number", nullable = false, length = 20)
    private String number;

    @Column(name = "validity_date", nullable = false)
    private LocalDate validityDate;

    @Column(name = "user_id")
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
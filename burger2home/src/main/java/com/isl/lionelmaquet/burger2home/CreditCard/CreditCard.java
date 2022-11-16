package com.isl.lionelmaquet.burger2home.CreditCard;

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

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "validity_date", nullable = false)
    private LocalDate validityDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
package com.isl.lionelmaquet.burger2home.Address;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "address")
@JsonSerialize
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "zipcode", nullable = false)
    private Integer zipcode;

    @Column(name = "street", nullable = false, length = 45)
    private String street;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "extension")
    private Integer extension;

    @Column(name = "note")
    private String note;

    @Column(name = "user_id")
    private Integer userId;

//    @Transient
//    private Map<String,String> links;
//
//    @PostLoad
//    private void fillLinks(){
//        links = new HashMap<>(){{
//            put("user", "users/" + getUserId());
//        }};
//    }
//
//    public Map<String, String> getLinks() {
//        return links;
//    }
//
//    public void setLinks(Map<String, String> links) {
//        this.links = links;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
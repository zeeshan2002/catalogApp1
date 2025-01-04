package com.zeeshan.catalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
    @ManyToMany
    private List<Product> products = new ArrayList<>();
//    (fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
//    @JsonIgnore

//    public Integer getId() {
//        return id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public List<String> getRoles() {
//        return roles;
//    }
//
//    public List<Product> getProducts() {
//        return products;
//    }
}

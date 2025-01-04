package com.zeeshan.catalog.service;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.UserDTO;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.repository.ProductRepo;
import com.zeeshan.catalog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    public List<Users> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Transactional
    public List<Product> getUserWithProducts(String username) {
        Users user = userRepo.getUserByUsername(username);
//        return user != null ? user.getProducts(): null;
        return user.getProducts();
    }

    @Transactional
    public void saveUser(Users user) {
        user.setRoles(Arrays.asList("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepo.save(user);
    }

    @Transactional
    public Optional<Users> deleteUser(String username) {
        Users users = userRepo.getUserByUsername(username);
        Integer id = users.getId();
        users.getProducts().clear();
        this.userRepo.save(users);
        this.userRepo.deleteById(id);
        return Optional.of(users);
    }

    @Transactional
    public Product saveProductById(String username, Integer prodId){
        Product product = productRepo.findById(prodId).get();
        Users user = userRepo.getUserByUsername(username);
        List<Product> productList = user.getProducts();
        productList.add(product);
        user.setProducts(productList);
        userRepo.save(user);
        return product;
    }

    @Transactional
    public void updateUser(String username, Users newUser) {
        Users oldUser = userRepo.getUserByUsername(username);
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepo.save(oldUser);
    }
}

//@Transactional
//    public void saveProduct(Product product, String username) {
//        Users user = this.userRepo.getUserByUsername(username);
//        this.productService.saveProduct(product);
//        List<Product> productList = user.getProducts();
//        productList.add(product);
//        this.userRepo.save(user);
//    }

//    public List<Product> getAllProductsOfUser(String username) {
//        Users user = this.userRepo.getUserByUsername(username);
//        return user.getProducts();
//    }
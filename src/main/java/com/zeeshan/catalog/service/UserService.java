package com.zeeshan.catalog.service;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.UserDTO;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.repository.ProductRepo;
import com.zeeshan.catalog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepo productRepo;

    public List<Users> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Transactional
    public UserDTO getUserWithProducts(String username) {
        Users user = userRepo.getUserByUsername(username);
        return user != null ? new UserDTO(user.getUsername(), user.getProducts().stream().map(Product::toString).collect(Collectors.toList())) : new UserDTO();
    }

    @Transactional
    public void saveUser(Users user) {
        user.setRoles(Collections.singletonList("USER"));
        this.userRepo.save(user);
    }

    @Transactional
    public Optional<Users> deleteUser(Integer id) {
        Optional<Users> users = this.userRepo.findById(id);
        if (users.isPresent()) {
            users.get().getProducts().clear();
            this.userRepo.save(users.get());
            this.userRepo.deleteById(id);
            return users;
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public void saveProduct(Product product, String username) {
        Users user = this.userRepo.getUserByUsername(username);
        this.productService.saveProduct(product);
        List<Product> productList = user.getProducts();
        productList.add(product);
        this.userRepo.save(user);
    }

    public List<Product> getAllProductsOfUser(String username) {
        Users user = this.userRepo.getUserByUsername(username);
        return user.getProducts();
    }

    public Product saveProductById(String username, Integer prodId){
        Product product = productRepo.findById(prodId).get();
        Users user = userRepo.getUserByUsername(username);
        List<Product> productList = user.getProducts();
        productList.add(product);
        user.setProducts(productList);
        userRepo.save(user);
        return product;
    }
}


package com.zeeshan.catalog.controller;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.service.ProductService;
import com.zeeshan.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService service;

    // get users
    @GetMapping("/get-all-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }
    @PostMapping("/create-admin")
    public ResponseEntity<?> saveAdminUser(@RequestBody Users user){
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // products
    @PostMapping("/add-product")
    public String saveProduct(@RequestBody Product product) {
        this.service.saveProduct(product);
        return "Product added successfully";
    }

    @PutMapping({"/{id}"})
    public String updateProduct(@RequestBody Product product, @PathVariable Integer id) {
        this.service.updateProduct(product, id);
        return "Product updated successfully";
    }

    @DeleteMapping({"/{id}"})
    public Optional<Product> deleteProduct(@PathVariable Integer id) {
        return this.service.deleteProduct(id);
    }
}

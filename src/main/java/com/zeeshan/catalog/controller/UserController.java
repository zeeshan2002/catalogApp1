package com.zeeshan.catalog.controller;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.UserDTO;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.service.ProductService;
import com.zeeshan.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/users"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;



    @GetMapping({"/products/{username}"})
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        UserDTO user = this.userService.getUserWithProducts(username);
        return !user.equals(new UserDTO()) ? new ResponseEntity<>(user, HttpStatus.OK) :
                new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product/{username}, {prodId}")
    public ResponseEntity<?> saveProductById(@PathVariable String username, @PathVariable Integer prodId){
        Product product = userService.saveProductById(username, prodId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @PostMapping({"/products/{username}"})
    public ResponseEntity<?> saveProduct(@RequestBody Product product, @PathVariable String username) {
        this.userService.saveProduct(product, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody Users user) {
        this.userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping({"/delete/{id}"})
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<Users> users = this.userService.deleteUser(id);
        return users.isPresent() ? new ResponseEntity<>(users, HttpStatus.NO_CONTENT) :
                new ResponseEntity<>("User not found with this id: " + id, HttpStatus.NOT_FOUND);
    }
}

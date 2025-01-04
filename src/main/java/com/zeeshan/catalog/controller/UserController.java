package com.zeeshan.catalog.controller;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.UserDTO;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.service.ProductService;
import com.zeeshan.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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



    @GetMapping({"/products"})
    public ResponseEntity<?> getProductByUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<Product> products = this.userService.getUserWithProducts(username);
            return new ResponseEntity<>(products, HttpStatus.OK);
//            return !user.equals(new UserDTO()) ? new ResponseEntity<>(user, HttpStatus.OK) :
//                    new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/product/{prodId}")
    public ResponseEntity<?> saveProductById(@PathVariable Integer prodId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Product product = userService.saveProductById(username, prodId);
            return new ResponseEntity<>("Product added successfully: " + product, HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Users user){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if(authentication.isAuthenticated())
                userService.updateUser(username, user);
            return new ResponseEntity<>("Updated user successfully: ", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>("Something went wrong: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/delete"})
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<Users> users = this.userService.deleteUser(username);
            return users.isPresent() ? new ResponseEntity<>(users, HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>("User not found with this name: " + username, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

//    @PostMapping({"/products/{username}"})
//    public ResponseEntity<?> saveProduct(@RequestBody Product product, @PathVariable String username) {
//        this.userService.saveProduct(product, username);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }



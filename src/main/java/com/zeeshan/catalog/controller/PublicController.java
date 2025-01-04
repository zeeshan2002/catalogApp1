package com.zeeshan.catalog.controller;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.Users;
import com.zeeshan.catalog.repository.UserRepo;
import com.zeeshan.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> create_user(@RequestBody Users users) {
        userService.saveUser(users);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

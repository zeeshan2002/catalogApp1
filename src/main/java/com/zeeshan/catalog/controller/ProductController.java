package com.zeeshan.catalog.controller;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.ProductWrapper;
import com.zeeshan.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping({"/product"})
public class ProductController {
    @Autowired
    public ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        return new ResponseEntity<>(this.service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping({"/precise"})
    public ResponseEntity<List<ProductWrapper>> getProductInformatively() {
        return new ResponseEntity<>(this.service.getProductInformatively(), HttpStatus.OK);
    }

    @GetMapping({"/id/{id}"})
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        try {
            this.service.getProductById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception var3) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping({"/category/{category}"})
    public List<Product> getProductByCategory(@PathVariable String category) {
        return this.service.getProductByCategory(category);
    }

    @GetMapping({"/priceRange/{a},{b}"})
    public List<Product> getProductByRange(@PathVariable Float a, @PathVariable Float b) {
        return this.service.getProductByRange(a, b);
    }

}
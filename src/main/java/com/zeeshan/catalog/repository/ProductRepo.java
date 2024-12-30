package com.zeeshan.catalog.repository;

import com.zeeshan.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> getProductByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :a AND :b")
    List<Product> getProductByRange(Float a, Float b);
}

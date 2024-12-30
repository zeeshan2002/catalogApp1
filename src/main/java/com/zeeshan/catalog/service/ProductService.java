package com.zeeshan.catalog.service;

import com.zeeshan.catalog.model.Product;
import com.zeeshan.catalog.model.ProductWrapper;
import com.zeeshan.catalog.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    public ProductRepo repo;

    public List<Product> getAllProducts() {
        return this.repo.findAll();
    }

    public List<ProductWrapper> getProductInformatively() {
        List<Product> productList = repo.findAll();
        List<ProductWrapper> pwlist = new ArrayList<>();
        for(Product product : productList){
            ProductWrapper pw = new ProductWrapper();
            pw.setName(product.getName());
            pw.setCategory(product.getCategory());
            pw.setPrice(product.getPrice());
            pwlist.add(pw);
        }
        return pwlist;
    }

    public Optional<Product> getProductById(Integer id) {
        return this.repo.findById(id);
    }

    public List<Product> getProductByCategory(String category) {
        return this.repo.getProductByCategory(category);
    }

    public void saveProduct(Product product) {
        this.repo.save(product);
    }

    public void updateProduct(Product product, Integer id) {
        Product old = this.repo.findById(id).get();
        old.setName(product.getName());
        old.setCategory(product.getCategory());
        old.setDescription(product.getDescription());
        old.setPrice(product.getPrice());
        this.repo.save(old);
    }

    public Optional<Product> deleteProduct(Integer id) {
        Optional<Product> product = this.repo.findById(id);
        this.repo.deleteById(id);
        return product;
    }

    public List<Product> getProductByRange(Float a, Float b) {
        List<Product> products = this.repo.getProductByRange(a, b);
        return products;
    }
}


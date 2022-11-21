package com.example.demo.server;

import com.example.demo.entity.Product;

import java.util.List;

public interface ProductServer {
    Product findById(long id);

    Product saveProduct(Product product);


    void deleteProductById(long id);

    List<Product> findAllProducts();
}

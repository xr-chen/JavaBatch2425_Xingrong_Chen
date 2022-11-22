package com.example.demo.server;

import com.example.demo.dao.ProductRepository;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServerImple implements ProductServer {

    private final ProductRepository productRepository;

    @Autowired
    ProductServerImple(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(new Product(product.getName(), product.getType(), product.getPrice(), product.getQuantity()));
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }
}

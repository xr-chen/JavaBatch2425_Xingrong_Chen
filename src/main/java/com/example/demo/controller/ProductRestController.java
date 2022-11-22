package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.server.ProductServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductRestController {
    private final ProductServer productServer;

    private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    ProductRestController(ProductServer productServer) {
        this.productServer = productServer;
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        Product product = productServer.findById(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> res = productServer.findAllProducts();
        if (res == null || res.isEmpty()) {
            throw new ProductNotFoundException();
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") long id) {
        Product product = productServer.findById(id);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        productServer.deleteProductById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "PRODUCT_DELETED");
        response.put("body", product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Product> updateUser(@PathVariable("id") long id, @RequestBody Product product){
        Product productObj = productServer.findById(id);

        if (productObj == null) {
            throw new ProductNotFoundException();
        }

        productObj.setName(product.getName());
        productObj.setPrice(product.getPrice());
        productObj.setType(product.getType());
        productObj.setQuantity(product.getQuantity());

        productServer.updateProduct(productObj);
        return new ResponseEntity<>(productObj, HttpStatus.OK);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Object> createUser(@Validated @RequestBody Product product, UriComponentsBuilder ucBuilder) {
        Product saveProduct = productServer.saveProduct(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/products/{id}").buildAndExpand(product.getId()).toUri());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "PRODUCT_CREATED");
        response.put("body", saveProduct);
        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> exceptionHandlerUserNotFound(Exception ex) {
        logger.error("Cannot find product");
        Map<String, Object> error = new HashMap<>();
        error.put("code", HttpStatus.NOT_FOUND.value());
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

package com.example.demo.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("PRODUCT_NOT_FOUND");
    }
}

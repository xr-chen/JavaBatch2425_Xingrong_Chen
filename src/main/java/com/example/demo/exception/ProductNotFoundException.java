package com.example.demo.exception;

public class ProductNotFoundException extends ProductException {

    public ProductNotFoundException() {
        super("PRODUCT_NOT_FOUND");
    }
}

package com.example.demo.exception;

public class ProductException extends RuntimeException {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ProductException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ProductException() {
        super();
    }
}

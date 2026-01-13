package com.callmextrm.product_service.exception;

public class Discontinued extends RuntimeException {
    public Discontinued(String message) {
        super(message);
    }

}
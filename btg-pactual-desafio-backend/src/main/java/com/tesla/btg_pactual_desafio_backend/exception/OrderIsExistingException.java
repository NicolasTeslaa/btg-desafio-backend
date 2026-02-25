package com.tesla.btg_pactual_desafio_backend.exception;

public class OrderIsExistingException extends RuntimeException {
    public OrderIsExistingException(String message) {
        super(message);
    }
}

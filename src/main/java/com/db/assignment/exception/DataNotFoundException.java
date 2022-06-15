package com.db.assignment.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(long orderId) {
        super(String.format("NACE data with order id %d not found", orderId));
    }
}

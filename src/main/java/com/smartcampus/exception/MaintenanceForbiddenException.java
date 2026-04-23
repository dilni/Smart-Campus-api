package com.smartcampus.exception;

public class MaintenanceForbiddenException extends RuntimeException {
    public MaintenanceForbiddenException(String message) {
        super(message);
    }
}

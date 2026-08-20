package com.AbnerTest.ecommerce_test.exceptions;

import com.auth0.jwt.exceptions.JWTCreationException;

public class Exceptions {
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
    public static class InvalidTokenCredenceException extends RuntimeException {
        public InvalidTokenCredenceException(String message, JWTCreationException e) {super(message, e);}
    }
    public static class OutOfStockException extends RuntimeException {
        public OutOfStockException(String message) {super(message);}
    }
    public static class InvalidCartItemPrice extends RuntimeException {
        public InvalidCartItemPrice(String message) {super(message);}
    }
    public static class DuplicateLoginException extends RuntimeException {
        public DuplicateLoginException(String message) {super(message);}
    }
}

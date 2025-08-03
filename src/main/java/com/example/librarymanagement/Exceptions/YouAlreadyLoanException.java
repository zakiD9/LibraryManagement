package com.example.librarymanagement.Exceptions;

public class YouAlreadyLoanException extends RuntimeException {
    
    public YouAlreadyLoanException(String message) {
        super(message);
    }
}

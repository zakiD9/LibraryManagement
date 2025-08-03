package com.example.librarymanagement.Exceptions;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(Long bookId) {
        super("Book with ID " + bookId + " not found.");
    }
    
}

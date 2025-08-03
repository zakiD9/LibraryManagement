package com.example.librarymanagement.dto;

import com.example.librarymanagement.entity.Book;

import lombok.Data;

@Data
public class BookDTO {
    private Long bookId;
    private String title;
    private String author;

    public BookDTO(Book book) {
        this.bookId = book.getBookId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}

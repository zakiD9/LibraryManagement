package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.service.BookService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<BookDTO>> searchBookByTitle(@RequestParam String title,@RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        if(page < 1) {
            page = 1;
        }
        if(size < 1){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<BookDTO> books = bookService.searchBookByTitle(title,pageable);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        return bookDTO != null ? ResponseEntity.ok(bookDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book) {
        BookDTO createdBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
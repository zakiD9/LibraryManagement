package com.example.librarymanagement.service;

import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanItemRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LoanItemRepository loanItemRepository;

    public BookService(BookRepository bookRepository, LoanItemRepository loanItemRepository) {
        this.bookRepository = bookRepository;
        this.loanItemRepository = loanItemRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
    return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
}

    public Book addBook(Book book) {
        if(bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Book with title '" + book.getTitle() + "' already exists.");
        }
        return bookRepository.save(book);
    }

    public String updateBookQuantity(Long bookId, Integer newQuantity) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book updatedBook = book.get();
            updatedBook.setAvailableCopies(newQuantity);
            bookRepository.save(updatedBook);
            return "Book quantity updated successfully.";
        } else {
            return "Book not found.";
        }
    }

    public void deleteBook(Long id) {
        if (loanItemRepository.existsByBookBookIdAndLoanStatusFalse(id)) {
        throw new IllegalArgumentException("Cannot delete book: it is associated with an active loan.");
    }
    if (!bookRepository.existsById(id)) {
        throw new ResourceNotFoundException("Book not found with id: " + id);
    }

        bookRepository.deleteById(id);
    }
}

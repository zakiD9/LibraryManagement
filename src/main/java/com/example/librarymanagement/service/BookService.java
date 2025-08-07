package com.example.librarymanagement.service;

import com.example.librarymanagement.Exceptions.BadRequestException;
import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LoanItemRepository loanItemRepository;

    public BookService(BookRepository bookRepository, LoanItemRepository loanItemRepository) {
        this.bookRepository = bookRepository;
        this.loanItemRepository = loanItemRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(book))
                .collect(Collectors.toList());
    }

    public Page<BookDTO> searchBookByTitle(String title,org.springframework.data.domain.Pageable pageable){
        Page<Book> booksPage;
        if (title != null && !title.isEmpty()) {
        booksPage = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    } else {
        booksPage = bookRepository.findAll(pageable);
    }
        return booksPage.map(BookDTO::new);
    }

    public BookDTO getBookById(Long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    return new BookDTO(book);
}

    public Page<BookDTO> getAllBooksByPagination(org.springframework.data.domain.Pageable pageable) {
        return bookRepository.findAll(pageable)
            .map(BookDTO::new);
    }

    public BookDTO addBook(Book book) {
        if(bookRepository.findByTitle(book.getTitle()).isPresent()) {
            throw new ResourceNotFoundException("Book with title '" + book.getTitle() + "' already exists.");
        }
        return new BookDTO(bookRepository.save(book));
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
        throw new BadRequestException("Cannot delete book: it is associated with an active loan.");
    }
    if (!bookRepository.existsById(id)) {
        throw new ResourceNotFoundException("Book not found with id: " + id);
    }

        bookRepository.deleteById(id);
    }
}

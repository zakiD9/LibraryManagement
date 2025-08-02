package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.LoanItem;
import com.example.librarymanagement.entity.Loan;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanItemRepository;
import com.example.librarymanagement.repository.LoanRepository;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

@Service
public class LoanItemService {

    private final LoanItemRepository loanItemRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public LoanItemService(LoanItemRepository loanItemRepository, BookRepository bookRepository, LoanRepository loanRepository) {
        this.loanItemRepository = loanItemRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }
    public LoanItem addLoanItem(LoanItem loanItem, Long bookId, Long loanId) {
         Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new RuntimeException("Loan not found"));
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        loanItem.setBook(book);
        loanItem.setLoan(loan);
        loanItem.setReturnDate(LocalDate.now().plusWeeks(1));
        loanItem.setIsReturned(false);
        
        return loanItemRepository.save(loanItem);
    }

    public void deleteLoanItem(Long id) {
        loanItemRepository.deleteById(id);
    }
}

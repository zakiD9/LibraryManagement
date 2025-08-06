package com.example.librarymanagement.service;

import com.example.librarymanagement.entity.LoanItem;
import com.example.librarymanagement.entity.Loan;
import com.example.librarymanagement.Exceptions.BadRequestException;
import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.dto.LoanItemDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanItemRepository;
import com.example.librarymanagement.repository.LoanRepository;

import java.time.LocalDate;
import java.util.Optional;

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
    public LoanItemDTO addLoanItem( Long bookId, Long loanId) {
        LoanItem loanItem = new LoanItem();
         Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        if (book.getAvailableCopies() <= 0) {
            throw new BadRequestException("No available copies for this book");
        }
        if (loan.getStatus()) {
            throw new BadRequestException("Loan is already completed");
        }
        loanItem.setQuantity(loanItem.getQuantity() + 1);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        loanItem.setBook(book);
        loanItem.setLoan(loan);
        loanItem.setReturnDate(LocalDate.now().plusWeeks(1));
        loanItem.setIsReturned(false);

        return new LoanItemDTO(loanItemRepository.save(loanItem));
    }

    public Optional<LoanItemDTO> getLoanItemById(Long id) {
    if (!loanItemRepository.existsById(id)) {
        throw new ResourceNotFoundException("LoanItem not found with id: " + id);
    }
    return loanItemRepository.findById(id).map(LoanItemDTO::new);
}

    public void returnLoanItem(Long loanItemId) {
    LoanItem loanItem = loanItemRepository.findById(loanItemId)
        .orElseThrow(() -> new ResourceNotFoundException("LoanItem not found with id: " + loanItemId));

    if (Boolean.TRUE.equals(loanItem.getIsReturned())) {
        throw new BadRequestException("LoanItem is already returned");
    }

    loanItem.setIsReturned(true);
    loanItemRepository.save(loanItem);

    Loan loan = loanItem.getLoan();
    boolean allReturned = loan.getLoanItems().stream()
        .allMatch(LoanItem::getIsReturned);

    if (allReturned) {
        loan.setStatus(true);
        loanRepository.save(loan);
    }
}

    public void deleteLoanItem(Long id) {
    LoanItem loanItem = loanItemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("LoanItem not found with id: " + id));

    if (!Boolean.TRUE.equals(loanItem.getIsReturned())) {
        throw new BadRequestException("Cannot delete LoanItem: it has not been returned yet.");
    }

    loanItemRepository.deleteById(id);
}

}

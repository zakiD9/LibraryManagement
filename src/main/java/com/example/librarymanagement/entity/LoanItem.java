package com.example.librarymanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class LoanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LoanItemId;

    private Integer quantity;
    private LocalDate returnDate;
    private Boolean isReturned;


    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Boolean isReturned) {
        this.isReturned = isReturned;
    }
    public LoanItem() {
    }

    public Long getLoanItemId() {
        return LoanItemId;
    }

    public void setLoanItemId(Long loanItemId) {
        LoanItemId = loanItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    
}

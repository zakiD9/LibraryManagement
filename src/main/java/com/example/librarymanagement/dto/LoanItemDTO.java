package com.example.librarymanagement.dto;

import com.example.librarymanagement.entity.LoanItem;

import lombok.Data;

@Data
public class LoanItemDTO {
    
    private Long loanItemId;
    private Long bookId;
    private Long loanId;
    private int quantity;

    public LoanItemDTO(LoanItem loanItem) {
        this.loanItemId = loanItem.getLoanItemId();
        this.bookId = loanItem.getBook() != null ? loanItem.getBook().getBookId() : null;
        this.loanId = loanItem.getLoan() != null ? loanItem.getLoan().getLoanId() : null;
        this.quantity = loanItem.getQuantity();
    }
}

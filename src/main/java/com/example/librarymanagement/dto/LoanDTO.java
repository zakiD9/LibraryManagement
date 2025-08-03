package com.example.librarymanagement.dto;

import lombok.Data;
import java.time.LocalDate;

import com.example.librarymanagement.entity.Loan;
@Data
public class LoanDTO {
    
    private Long loanId;
    private Long userId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private boolean status;

    public LoanDTO(Loan loan) {
        this.loanId = loan.getLoanId();
        this.userId = loan.getUser() != null ? loan.getUser().getUserId() : null;
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.status = loan.getStatus();
    }

}

package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoanDTO;
import com.example.librarymanagement.entity.Loan;
import com.example.librarymanagement.entity.LoanItem;
import com.example.librarymanagement.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping
    public LoanDTO addLoan(@RequestParam Long userId) {
        return loanService.addLoan(userId);
    }

    @GetMapping
    public List<LoanDTO> getAllLoans() {
        return loanService.getAllLoans();
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
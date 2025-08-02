package com.example.librarymanagement.controller;

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

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public Optional<Loan> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PostMapping
    public Loan addLoan(@RequestBody List<LoanItem> loanItems, @RequestParam Long userId) {
        return loanService.addLoan(loanItems, userId);
    }

    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }
}
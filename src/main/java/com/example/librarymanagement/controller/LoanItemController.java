package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoanItemDTO;
import com.example.librarymanagement.service.LoanItemService;

import java.util.Optional;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-items")
public class LoanItemController {

    private final LoanItemService loanItemService;

    public LoanItemController(LoanItemService loanItemService) {
        this.loanItemService = loanItemService;
    }

    @PostMapping
    public LoanItemDTO addLoanItem(
                                @RequestParam Long bookId,
                                @RequestParam Long loanId) {
        return loanItemService.addLoanItem(bookId, loanId);
    }

    @GetMapping("/{id}")
    public Optional<LoanItemDTO> getLoanItemById(@PathVariable Long id) {
        return loanItemService.getLoanItemById(id); }

    @PutMapping("/{loanItemId}/return")
    public void returnLoanItem(@PathVariable Long loanItemId) {
        loanItemService.returnLoanItem(loanItemId);
    }

    @DeleteMapping("/{id}")
    public void deleteLoanItem(@PathVariable Long id) {
        loanItemService.deleteLoanItem(id);
    }
}
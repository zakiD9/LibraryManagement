package com.example.librarymanagement.controller;

import com.example.librarymanagement.entity.LoanItem;
import com.example.librarymanagement.service.LoanItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-items")
public class LoanItemController {

    private final LoanItemService loanItemService;

    public LoanItemController(LoanItemService loanItemService) {
        this.loanItemService = loanItemService;
    }

    @PostMapping
    public LoanItem addLoanItem(@RequestBody LoanItem loanItem,
                                @RequestParam Long bookId,
                                @RequestParam Long loanId) {
        return loanItemService.addLoanItem(loanItem, bookId, loanId);
    }

    @DeleteMapping("/{id}")
    public void deleteLoanItem(@PathVariable Long id) {
        loanItemService.deleteLoanItem(id);
    }
}
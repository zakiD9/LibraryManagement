package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoanItemDTO;
import com.example.librarymanagement.service.LoanItemService;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-items")
public class LoanItemController {

    private final LoanItemService loanItemService;

    public LoanItemController(LoanItemService loanItemService) {
        this.loanItemService = loanItemService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<LoanItemDTO> addLoanItem(
                                @RequestParam Long bookId,
                                @RequestParam Long loanId) {
        LoanItemDTO loanItemDTO = loanItemService.addLoanItem(bookId, loanId);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanItemDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<LoanItemDTO> getLoanItemById(@PathVariable Long id) {
        Optional<LoanItemDTO> loanItemDTO = loanItemService.getLoanItemById(id);
        return loanItemDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{loanItemId}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> returnLoanItem(@PathVariable Long loanItemId) {
        loanItemService.returnLoanItem(loanItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteLoanItem(@PathVariable Long id) {
        loanItemService.deleteLoanItem(id);
        return ResponseEntity.noContent().build();
    }
}
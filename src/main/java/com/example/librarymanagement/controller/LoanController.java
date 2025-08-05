package com.example.librarymanagement.controller;

import com.example.librarymanagement.dto.LoanDTO;
import com.example.librarymanagement.service.LoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping
    public ResponseEntity<LoanDTO> addLoan(@RequestParam Long userId) {
        LoanDTO loanDTO = loanService.addLoan(userId);
        return ResponseEntity.status(201).body(loanDTO);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }
}
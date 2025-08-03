package com.example.librarymanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.librarymanagement.dto.LoanDTO;
import com.example.librarymanagement.entity.Loan;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.entity.LoanItem;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }
    
    public List<LoanDTO> getAllLoans(){
        return loanRepository.findAll().stream()
            .map(LoanDTO::new)
            .toList();
    }

    public Optional<LoanDTO> getLoanById(Long id){
        return loanRepository.findById(id).map(LoanDTO::new);
    }

    public LoanDTO addLoan(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Loan loan = new Loan();
    loan.setStatus(false);
    loan.setUser(user);

    return new LoanDTO(loanRepository.save(loan));
}

    public void deleteLoan(Long id){
        loanRepository.deleteById(id);
    }
}

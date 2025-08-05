package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByUserUserIdAndStatusFalse(Long userId);
}

package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanItemRepository extends JpaRepository<LoanItem, Long> {
}

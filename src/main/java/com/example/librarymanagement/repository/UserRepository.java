package com.example.librarymanagement.repository;
import com.example.librarymanagement.entity.User;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

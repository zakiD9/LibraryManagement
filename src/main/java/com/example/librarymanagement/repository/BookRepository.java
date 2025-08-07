package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Book;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findAll(Pageable pageable);
    Optional<Book> findByTitle(String title);
}

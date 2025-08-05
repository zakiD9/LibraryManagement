package com.example.librarymanagement.repository;

import com.example.librarymanagement.entity.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}

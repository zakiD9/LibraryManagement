package com.example.librarymanagement.service;

import com.example.librarymanagement.Exceptions.BadRequestException;
import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.dto.BookDTO;
import com.example.librarymanagement.dto.UserDTO;
import com.example.librarymanagement.entity.Book;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public UserService(UserRepository userRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Cacheable(value = "users", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<UserDTO> searchUserByName(String name, org.springframework.data.domain.Pageable pageable) {
        Page<User> usersPage;
        if (name != null && !name.isEmpty()) {
            usersPage = userRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            usersPage = userRepository.findAll(pageable);
        }
        return usersPage.map(UserDTO::new);
    }

    @Cacheable(value = "user", key = "#id")
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(user -> new UserDTO(user));
    }

    @Transactional
    public UserDTO addUser(User user) {
        if (user.getEmail() == null || userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email is required and must be unique.");
        }
        return new UserDTO(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        if (loanRepository.existsByUserUserIdAndStatusFalse(id)) {
            throw new BadRequestException("Cannot delete user: user has an active loan.");
        }

        userRepository.deleteById(id);
    }
}

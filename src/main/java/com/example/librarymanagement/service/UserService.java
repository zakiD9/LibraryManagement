package com.example.librarymanagement.service;

import com.example.librarymanagement.Exceptions.BadRequestException;
import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.dto.UserDTO;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;
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


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(user -> new UserDTO(user));
    }

    public UserDTO addUser(User user) {
        if (user.getEmail() == null || userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email is required and must be unique.");
        }
        return new UserDTO(userRepository.save(user));
    }

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

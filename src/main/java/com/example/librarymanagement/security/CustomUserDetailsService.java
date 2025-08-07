package com.example.librarymanagement.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.librarymanagement.Exceptions.ResourceNotFoundException;
import com.example.librarymanagement.entity.User;
import com.example.librarymanagement.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public boolean userExistsByEmail(String email) {
    return userRepository.existsByEmail(email);
}

public void saveUser(User user) {
    userRepository.save(user);
}



    @Override
public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
    User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

    String role ="ROLE_" +  user.getRole().name().toUpperCase();

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        List.of(new SimpleGrantedAuthority(role))
    );
}

}

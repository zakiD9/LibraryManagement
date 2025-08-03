package com.example.librarymanagement.dto;

import com.example.librarymanagement.entity.User;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String username;
    private String email;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getFullName();
        this.email = user.getEmail();
    }

}

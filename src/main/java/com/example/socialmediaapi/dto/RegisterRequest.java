package com.example.socialmediaapi.dto;

import com.example.socialmediaapi.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO Запрос на регистрацию
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String login;
    private String password;
    private Role role;
}
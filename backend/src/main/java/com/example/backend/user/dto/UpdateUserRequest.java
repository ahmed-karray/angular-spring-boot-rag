package com.example.backend.user.dto;

import com.example.backend.user.entity.Department;
import com.example.backend.user.entity.Gender;
import com.example.backend.user.entity.Role;
import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
        String username,
        @Email String email,
        Role role,
        String firstName,
        String lastName,
        String phoneNumber,
        Department department,
        Gender gender,
        Integer age
) {}
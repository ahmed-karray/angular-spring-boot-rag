package com.example.backend.dto;

import com.example.backend.entity.Department;
import com.example.backend.entity.Gender;
import com.example.backend.entity.Role;
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
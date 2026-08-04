package com.example.backend.dto;

import com.example.backend.entity.Department;
import com.example.backend.entity.Gender;
import com.example.backend.entity.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        Role role,
        String firstName,
        String lastName,
        String phoneNumber,
        Department department,
        Gender gender,
        Integer age,
        LocalDateTime createdAt
) {}
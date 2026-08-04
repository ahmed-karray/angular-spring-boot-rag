package com.example.backend.dto;

import com.example.backend.entity.Department;
import com.example.backend.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        String username,
        @Email String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Department department,
        Gender gender,
        Integer age,
        @Size(min = 6, message = "Password must be at least 6 characters") String newPassword,
        String currentPassword
) {}
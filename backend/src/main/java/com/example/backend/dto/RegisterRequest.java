package com.example.backend.dto;

import com.example.backend.entity.Department;
import com.example.backend.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        String firstName,
        String lastName,
        String phoneNumber,
        Department department,
        Gender gender,
        Integer age,
        String recaptchaToken
) {}
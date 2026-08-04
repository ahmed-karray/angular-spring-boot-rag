package com.example.backend.service;

import com.example.backend.dto.UpdateProfileRequest;
import com.example.backend.dto.UpdateUserRequest;
import com.example.backend.dto.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userMapper.toResponse(user);
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }
        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }
        if (request.role() != null) {
            user.setRole(request.role());
        }
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }
        if (request.phoneNumber() != null) {
            user.setPhoneNumber(request.phoneNumber());
        }
        if (request.department() != null) {
            user.setDepartment(request.department());
        }
        if (request.gender() != null) {
            user.setGender(request.gender());
        }
        if (request.age() != null) {
            user.setAge(request.age());
        }

        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
    public UserResponse updateOwnProfile(User currentUser, UpdateProfileRequest request) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }
        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
        }
        if (request.phoneNumber() != null) {
            user.setPhoneNumber(request.phoneNumber());
        }
        if (request.department() != null) {
            user.setDepartment(request.department());
        }
        if (request.gender() != null) {
            user.setGender(request.gender());
        }
        if (request.age() != null) {
            user.setAge(request.age());
        }

        if (request.newPassword() != null && !request.newPassword().isBlank()) {
            if (request.currentPassword() == null || !passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            user.setPassword(passwordEncoder.encode(request.newPassword()));
        }

        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
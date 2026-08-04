package com.example.backend.mapper;

import com.example.backend.dto.UserResponse;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
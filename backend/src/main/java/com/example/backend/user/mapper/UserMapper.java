package com.example.backend.user.mapper;

import com.example.backend.user.dto.UserResponse;
import com.example.backend.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(User user);
}
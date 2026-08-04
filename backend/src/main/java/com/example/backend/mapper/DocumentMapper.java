package com.example.backend.mapper;

import com.example.backend.dto.DocumentResponse;
import com.example.backend.entity.Document;
import com.example.backend.entity.Tag;
import com.example.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "owner.username", target = "uploadedByUsername")
    @Mapping(source = "owner.department", target = "uploadedByDepartment")
    @Mapping(source = "sharedWithUsers", target = "sharedWithUsernames")
    DocumentResponse toResponse(Document document);

    default List<String> mapTags(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .sorted()
                .toList();
    }

    default List<String> mapUsersToUsernames(Set<User> users) {
        return users.stream()
                .map(User::getUsername)
                .sorted()
                .toList();
    }
}
package com.example.backend.document.dto;

import com.example.backend.document.entity.DocumentVisibility;
import com.example.backend.user.entity.Department;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentResponse(
        Long id,
        String filename,
        String contentType,
        Long size,
        LocalDateTime uploadedAt,
        String uploadedByUsername,
        Department uploadedByDepartment,
        List<String> tags,
        Long rootDocumentId,
        Integer versionNumber,
        boolean isLatest,
        DocumentVisibility visibility,
        List<String> sharedWithUsernames
) {}
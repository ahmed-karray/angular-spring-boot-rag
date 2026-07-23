package com.example.backend.document.dto;

import java.util.List;

public record ShareRequest(
        String visibility,
        List<String> usernames
) {}
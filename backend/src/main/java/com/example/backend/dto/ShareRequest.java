package com.example.backend.dto;

import java.util.List;

public record ShareRequest(
        String visibility,
        List<String> usernames
) {}
package com.example.backend.service;

import java.util.regex.Pattern;

public final class TextCleaner {

    private static final Pattern MULTI_NEWLINE = Pattern.compile("\\n{3,}");
    private static final Pattern TRAILING_SPACES = Pattern.compile("[ \\t]+\\n");
    private static final Pattern HYPHEN_LINEBREAK = Pattern.compile("(\\p{L})-\\n(\\p{L})");
    private static final Pattern MULTI_SPACE = Pattern.compile("[ \\t]{2,}");

    private TextCleaner() {
    }

    public static String clean(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return "";
        }

        String text = rawText;

        // Normalize line endings
        text = text.replace("\r\n", "\n").replace("\r", "\n");

        // Rejoin words split across a line break by a hyphen: "infor-\nmation" -> "information"
        text = HYPHEN_LINEBREAK.matcher(text).replaceAll("$1$2");

        // Strip trailing whitespace at the end of each line
        text = TRAILING_SPACES.matcher(text).replaceAll("\n");

        // Collapse runs of horizontal whitespace
        text = MULTI_SPACE.matcher(text).replaceAll(" ");

        // Collapse 3+ consecutive newlines down to a single paragraph break
        text = MULTI_NEWLINE.matcher(text).replaceAll("\n\n");

        // Trim leading/trailing whitespace on each line, preserving blank lines as paragraph breaks
        StringBuilder result = new StringBuilder();
        for (String line : text.split("\n", -1)) {
            result.append(line.strip()).append("\n");
        }

        return result.toString().strip();
    }
}
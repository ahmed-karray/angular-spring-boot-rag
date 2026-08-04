package com.example.backend.service;

import java.util.*;
import java.util.regex.Pattern;

public final class HeaderFooterStripper {

    private static final int EDGE_LINES_TO_CHECK = 3;   // check first/last N non-blank lines per page
    private static final double REPEAT_THRESHOLD = 0.6; // must repeat on >=60% of pages to count as boilerplate
    private static final Pattern DIGITS = Pattern.compile("\\d+");

    // Real headers/footers — page numbers, "Confidential", running titles, company
    // names, dates — are almost always short. A line long enough to be a genuine
    // prose sentence essentially never is a header/footer, even if it happens to
    // repeat (e.g. a document that duplicates the same paragraph across pages).
    // Gating candidacy on length keeps the detector from stripping real content
    // just because it repeated in the same edge position.
    private static final int MAX_BOILERPLATE_WORDS = 8;

    private HeaderFooterStripper() {
    }

    public static List<String> stripHeadersFooters(List<String> pages) {
        if (pages.size() < 3) {
            return pages; // not enough pages to detect repetition reliably
        }

        Map<String, Integer> counts = new HashMap<>();
        for (String page : pages) {
            for (String line : edgeLines(page)) {
                if (wordCount(line) > MAX_BOILERPLATE_WORDS) {
                    continue; // too long to plausibly be a header/footer — never a candidate
                }
                String normalized = normalize(line);
                if (!normalized.isEmpty()) {
                    counts.merge(normalized, 1, Integer::sum);
                }
            }
        }

        int threshold = (int) Math.ceil(pages.size() * REPEAT_THRESHOLD);
        Set<String> boilerplate = new HashSet<>();
        counts.forEach((line, count) -> {
            if (count >= threshold) boilerplate.add(line);
        });

        if (boilerplate.isEmpty()) {
            return pages;
        }

        List<String> result = new ArrayList<>();
        for (String page : pages) {
            StringBuilder sb = new StringBuilder();
            for (String line : page.split("\r?\n", -1)) {
                if (!boilerplate.contains(normalize(line))) {
                    sb.append(line).append("\n");
                }
            }
            result.add(sb.toString());
        }
        return result;
    }

    private static List<String> edgeLines(String page) {
        List<String> nonBlank = new ArrayList<>();
        for (String line : page.split("\n")) {
            if (!line.isBlank()) nonBlank.add(line);
        }
        List<String> edges = new ArrayList<>();
        int n = nonBlank.size();
        for (int i = 0; i < Math.min(EDGE_LINES_TO_CHECK, n); i++) edges.add(nonBlank.get(i));
        for (int i = Math.max(0, n - EDGE_LINES_TO_CHECK); i < n; i++) edges.add(nonBlank.get(i));
        return edges;
    }

    private static int wordCount(String line) {
        String trimmed = line.strip();
        if (trimmed.isEmpty()) return 0;
        return trimmed.split("\\s+").length;
    }

    private static String normalize(String line) {
        String collapsed = line.strip().toLowerCase().replaceAll("\\s+", " ");
        return DIGITS.matcher(collapsed).replaceAll("#");
    }
}
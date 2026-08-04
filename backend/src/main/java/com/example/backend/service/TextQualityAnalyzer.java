package com.example.backend.service;

import com.example.backend.dto.ExtractionDiagnostics;

import java.util.ArrayList;
import java.util.List;

public final class TextQualityAnalyzer {

    // If printable-character ratio drops below this, flag as likely garbled/mis-encoded.
    private static final double GARBLED_PRINTABLE_RATIO_THRESHOLD = 0.85;

    // Overall-ratio trigger: if more than this fraction of non-blank lines are short,
    // flag as fragmented. Lowered from 0.30 -> 0.12: on long documents a small pocket
    // of scrambled vertical/rotated text (e.g. a sidebar watermark) only moves the
    // overall ratio a few points, so 0.30 was letting real fragmentation through
    // undetected on anything longer than a couple of pages.
    private static final double FRAGMENTED_LINE_RATIO_THRESHOLD = 0.12;

    // Run-based trigger: independent of document length, if there's a contiguous
    // stretch of this many short lines in a row, that's the signature of vertically
    // stacked/rotated text getting split character-by-character — flag it even if
    // it's too small a fraction of a long document to trip the ratio threshold above.
    private static final int FRAGMENT_RUN_THRESHOLD = 5;

    private static final int FRAGMENT_LINE_MAX_LEN = 3;
    private static final int MIN_LINES_FOR_FRAGMENT_CHECK = 20;

    // A short line consisting solely of brace/paren/statement-terminator punctuation
    // (e.g. a lone "}" closing a Java block, or "();") is normal one-brace-per-line
    // code formatting, not evidence of vertically-split text. Vertical/rotated text
    // getting fragmented character-by-character spells out varying letters or digits
    // (a watermark string, an axis tick label); it is never a run of bare structural
    // punctuation. Excluding these avoids false positives on code-heavy documents
    // (e.g. nested for/if blocks closing several "}" lines in a row) without weakening
    // detection of genuine watermark/axis-label fragmentation, which never consists
    // purely of these characters.
    private static final String STRUCTURAL_CODE_CHARS = "{}();,";

    private TextQualityAnalyzer() {
    }

    public static ExtractionDiagnostics analyze(String text) {
        if (text == null) {
            text = "";
        }
        String trimmed = text.strip();

        int wordCount = countWords(trimmed);
        // Empty is a content signal, not a length signal: a short-but-real document
        // (e.g. a one-line sample letter) can legitimately be under any fixed character
        // threshold while still containing real words. Zero words is the actual failure
        // signature of a scanned/image-only PDF with no text layer.
        boolean empty = wordCount == 0;

        double printableRatio = printableRatio(trimmed);
        boolean looksGarbled = !empty && printableRatio < GARBLED_PRINTABLE_RATIO_THRESHOLD;

        List<String> nonBlankLines = nonBlankLines(trimmed);
        double fragmentedLineRatio = fragmentedLineRatio(nonBlankLines);
        int longestFragmentRun = longestFragmentRun(nonBlankLines);

        boolean enoughLinesToCheck = nonBlankLines.size() >= MIN_LINES_FOR_FRAGMENT_CHECK;
        boolean fragmentedLines = !empty && (
                (enoughLinesToCheck && fragmentedLineRatio >= FRAGMENTED_LINE_RATIO_THRESHOLD)
                        || longestFragmentRun >= FRAGMENT_RUN_THRESHOLD
        );

        return new ExtractionDiagnostics(
                empty,
                looksGarbled,
                fragmentedLines,
                round(printableRatio),
                round(fragmentedLineRatio),
                wordCount
        );
    }

    private static int countWords(String trimmed) {
        if (trimmed.isEmpty()) return 0;
        int count = 0;
        for (String w : trimmed.split("\\s+")) {
            if (!w.isBlank()) count++;
        }
        return count;
    }

    private static double printableRatio(String text) {
        if (text.isEmpty()) return 1.0;
        int printable = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (isCountedPrintable(c)) {
                printable++;
            }
        }
        return (double) printable / text.length();
    }

    private static boolean isCountedPrintable(char c) {
        if (c == '\n' || c == '\r' || c == '\t' || c == ' ') return true;
        if (c == '\uFFFD') return false; // Unicode replacement character = mis-decoded byte
        if (Character.isISOControl(c)) return false;
        return Character.isLetterOrDigit(c) || isCommonPunctuation(c);
    }

    private static boolean isCommonPunctuation(char c) {
        return ".,;:!?'\"()[]{}-–—/\\@#$%&*+=<>|~_°'\"‘’“”…•·".indexOf(c) >= 0;
    }

    private static List<String> nonBlankLines(String text) {
        List<String> lines = new ArrayList<>();
        if (text.isEmpty()) return lines;
        for (String rawLine : text.split("\n", -1)) {
            String line = rawLine.strip();
            if (!line.isEmpty()) lines.add(line);
        }
        return lines;
    }

    /** True if a line is short enough to be a fragmentation candidate AND isn't
     *  just bare code-structural punctuation (see STRUCTURAL_CODE_CHARS above). */
    private static boolean isFragmentCandidate(String line) {
        return line.length() <= FRAGMENT_LINE_MAX_LEN && !isStructuralCodePunctuationOnly(line);
    }

    private static boolean isStructuralCodePunctuationOnly(String line) {
        if (line.isEmpty()) return false;
        for (int i = 0; i < line.length(); i++) {
            if (STRUCTURAL_CODE_CHARS.indexOf(line.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    private static double fragmentedLineRatio(List<String> nonBlankLines) {
        if (nonBlankLines.isEmpty()) return 0.0;
        int fragments = 0;
        for (String line : nonBlankLines) {
            if (isFragmentCandidate(line)) fragments++;
        }
        return (double) fragments / nonBlankLines.size();
    }

    /** Longest run of consecutive short lines within the non-blank-line sequence
     *  (blank lines in the original text don't break a run — a vertical watermark
     *  is often interspersed with blank spacer lines but is still one visual block). */
    private static int longestFragmentRun(List<String> nonBlankLines) {
        int longest = 0;
        int current = 0;
        for (String line : nonBlankLines) {
            if (isFragmentCandidate(line)) {
                current++;
                longest = Math.max(longest, current);
            } else {
                current = 0;
            }
        }
        return longest;
    }

    private static double round(double value) {
        return Math.round(value * 1000.0) / 1000.0;
    }
}
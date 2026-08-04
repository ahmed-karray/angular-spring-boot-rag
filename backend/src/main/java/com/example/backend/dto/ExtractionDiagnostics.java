package com.example.backend.dto;


public record ExtractionDiagnostics(
        boolean empty,               // near-zero output (e.g. scanned/image-only PDF, no text layer)
        boolean looksGarbled,        // low printable-character ratio (font-encoding failure)
        boolean fragmentedLines,     // many suspiciously short lines (rotated/vertical text scrambled)
        double printableRatio,       // 0.0-1.0, fraction of characters that are normal printable text
        double fragmentedLineRatio,  // 0.0-1.0, fraction of non-blank lines that are <=3 chars long
        int wordCount
) {}
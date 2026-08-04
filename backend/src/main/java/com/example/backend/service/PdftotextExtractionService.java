package com.example.backend.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdftotextExtractionService {

    public String extractText(byte[] pdfData) throws IOException, InterruptedException {
        Path tempPdf = Files.createTempFile("upload-", ".pdf");
        Path tempTxt = Files.createTempFile("extracted-", ".txt");

        try {
            Files.write(tempPdf, pdfData);

            ProcessBuilder pb = new ProcessBuilder(
                    "pdftotext",
                    "-layout",
                    tempPdf.toAbsolutePath().toString(),
                    tempTxt.toAbsolutePath().toString()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            boolean finished = process.waitFor(30, java.util.concurrent.TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new IOException("pdftotext timed out");
            }
            if (process.exitValue() != 0) {
                String output = new String(process.getInputStream().readAllBytes());
                throw new IOException("pdftotext exited with code " + process.exitValue() + ": " + output);
            }

            return Files.readString(tempTxt);
        } finally {
            Files.deleteIfExists(tempPdf);
            Files.deleteIfExists(tempTxt);
        }
    }

    /**
     * Splits pdftotext's output into per-page text using the form-feed character
     * (\f) Poppler inserts between pages by default, regardless of -layout.
     *
     * expectedPageCount is passed in (from a PDFBox page count) so this can validate
     * the split actually matches reality — if pdftotext's form-feed output doesn't
     * line up (e.g. malformed PDF, embedded form feeds within page content), this
     * returns null rather than silently handing back a misaligned page list, so
     * callers know to fall back to a different extractor for this document.
     */
    public List<String> extractTextByPage(byte[] pdfData, int expectedPageCount) throws IOException, InterruptedException {
        String fullText = extractText(pdfData);
        String[] rawPages = fullText.split("\f", -1);

        List<String> pages = new ArrayList<>(List.of(rawPages));

        // pdftotext commonly emits a trailing form feed after the last page, which
        // produces one extra blank entry at the end of the split — trim it off.
        if (!pages.isEmpty() && pages.get(pages.size() - 1).isBlank()
                && pages.size() == expectedPageCount + 1) {
            pages.remove(pages.size() - 1);
        }

        if (pages.size() != expectedPageCount) {
            return null;
        }
        return pages;
    }
}
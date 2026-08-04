package com.example.backend.service;

import com.example.backend.dto.ExtractionDiagnostics;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ExtractionRouterService {

    private static final Logger log = Logger.getLogger(ExtractionRouterService.class.getName());

    private final TikaExtractionService tikaExtractionService;
    private final PdftotextExtractionService pdftotextExtractionService;
    private final TextExtractionService textExtractionService; // PDFBox
    private final OcrExtractionService ocrExtractionService;

    /**
     * method: "tika" / "pdftotext" / "pdfbox" / "ocr" if every page used the same
     * extractor, or "mixed" if different pages needed different extractors.
     * pages: always populated (one entry per page), regardless of which method(s)
     * won — this lets HeaderFooterStripper always run, unlike the old whole-document
     * approach where only a full-Tika win produced page-segmented output.
     */
    public record RoutedExtraction(String method, String text, List<String> pages) {}

    // A page needs at least this many real words before its text is trusted —
    // below this, it's almost always a title/stamp/header sitting on an otherwise
    // image-only page rather than genuine extracted content.
    private static final int MIN_WORDS_PER_PAGE = 15;

    public RoutedExtraction extract(byte[] pdfData) throws Exception {
        int pageCount = countPages(pdfData);

        List<String> tikaPages = tryGetPages(() -> tikaExtractionService.extractTextByPage(pdfData), "Tika");
        List<String> pdftotextPages = tryGetPages(
                () -> pdftotextExtractionService.extractTextByPage(pdfData, pageCount), "pdftotext");
        List<String> pdfboxPages = tryGetPages(() -> textExtractionService.extractTextByPage(pdfData), "PDFBox");

        List<String> finalPages = new ArrayList<>();
        Set<String> methodsUsed = new LinkedHashSet<>();
        boolean anyPageNeedsOcrUnavailable = false;
        boolean anyPageHasContent = false;

        for (int i = 0; i < pageCount; i++) {
            String tikaPage = pageOrNull(tikaPages, i);
            String pdftotextPage = pageOrNull(pdftotextPages, i);
            String pdfboxPage = pageOrNull(pdfboxPages, i);

            String chosenText;
            if (isGoodPage(tikaPage)) {
                chosenText = tikaPage;
                methodsUsed.add("tika");
            } else if (isGoodPage(pdftotextPage)) {
                chosenText = pdftotextPage;
                methodsUsed.add("pdftotext");
            } else if (isGoodPage(pdfboxPage)) {
                chosenText = pdfboxPage;
                methodsUsed.add("pdfbox");
            } else if (ocrExtractionService.isAvailable()) {
                chosenText = ocrPageSafely(pdfData, i);
                methodsUsed.add("ocr");
            } else {
                chosenText = "";
                anyPageNeedsOcrUnavailable = true;
            }

            if (chosenText != null && !chosenText.isBlank()) {
                anyPageHasContent = true;
            }
            finalPages.add(chosenText != null ? chosenText : "");
        }

        String method;
        if (!anyPageHasContent) {
            method = anyPageNeedsOcrUnavailable ? "needs_ocr" : "none";
        } else {
            method = methodsUsed.size() == 1 ? methodsUsed.iterator().next() : "mixed";
            if (anyPageNeedsOcrUnavailable) {
                log.warning("Document extracted with method=" + method
                        + " but at least one page needed OCR and Tesseract was unavailable — that page's content is missing.");
            }
        }

        return new RoutedExtraction(method, String.join("\n\n", finalPages), finalPages);
    }

    private String ocrPageSafely(byte[] pdfData, int pageIndex) {
        try {
            return ocrExtractionService.ocrPage(pdfData, pageIndex);
        } catch (Exception e) {
            log.log(Level.WARNING, "OCR threw on page " + (pageIndex + 1), e);
            return "";
        }
    }

    private String pageOrNull(List<String> pages, int index) {
        return (pages != null && index < pages.size()) ? pages.get(index) : null;
    }

    private boolean isGoodPage(String text) {
        if (text == null) return false;
        ExtractionDiagnostics d = TextQualityAnalyzer.analyze(text);
        return !d.empty() && !d.looksGarbled() && !d.fragmentedLines() && d.wordCount() >= MIN_WORDS_PER_PAGE;
    }

    private int countPages(byte[] pdfData) {
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            return Math.max(document.getNumberOfPages(), 1);
        } catch (Exception e) {
            log.log(Level.WARNING, "Could not count pages, defaulting to 1", e);
            return 1;
        }
    }

    @FunctionalInterface
    private interface PageSupplier {
        List<String> get() throws Exception;
    }

    private List<String> tryGetPages(PageSupplier supplier, String extractorName) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.log(Level.WARNING, extractorName + " page extraction threw, skipping for this document", e);
            return null;
        }
    }
}
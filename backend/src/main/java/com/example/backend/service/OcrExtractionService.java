package com.example.backend.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OcrExtractionService {

    private static final Logger log = Logger.getLogger(OcrExtractionService.class.getName());
    private static final int OCR_RENDER_DPI = 300;

    private final Tesseract tesseract;
    private final boolean available;

    public OcrExtractionService(@Value("${tesseract.datapath:}") String tessDataPath) {
        this.tesseract = new Tesseract();
        if (tessDataPath != null && !tessDataPath.isBlank()) {
            tesseract.setDatapath(tessDataPath);
        }
        this.available = checkAvailability();
    }

    private boolean checkAvailability() {
        try {
            tesseract.doOCR(new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB));
            return true;
        } catch (Throwable t) {
            log.warning("Tesseract not available or misconfigured: " + t.getMessage()
                    + " — documents needing OCR will stay in NEEDS_OCR status until this is fixed.");
            return false;
        }
    }

    public boolean isAvailable() {
        return available;
    }

    /** OCRs every page and joins results — kept for callers that just want full-document text. */
    public String extractText(byte[] pdfData) throws Exception {
        return String.join("\n\n", extractTextByPage(pdfData));
    }

    /** OCRs every page individually, returning one entry per page. */
    public List<String> extractTextByPage(byte[] pdfData) throws Exception {
        if (!available) {
            throw new IllegalStateException("Tesseract is not available on this environment.");
        }
        List<String> pages = new ArrayList<>();
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            PDFRenderer renderer = new PDFRenderer(document);
            int totalPages = document.getNumberOfPages();
            for (int i = 0; i < totalPages; i++) {
                pages.add(ocrRenderedPage(renderer, i));
            }
        }
        return pages;
    }

    /**
     * OCRs a single page by index (0-based). Used by the router to OCR only the
     * specific pages that need it, instead of re-OCRing an entire document when
     * just one page's text-layer extraction came up short.
     */
    public String ocrPage(byte[] pdfData, int pageIndexZeroBased) throws Exception {
        if (!available) {
            throw new IllegalStateException("Tesseract is not available on this environment.");
        }
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            PDFRenderer renderer = new PDFRenderer(document);
            return ocrRenderedPage(renderer, pageIndexZeroBased);
        }
    }

    private String ocrRenderedPage(PDFRenderer renderer, int pageIndexZeroBased) {
        try {
            BufferedImage image = renderer.renderImageWithDPI(pageIndexZeroBased, OCR_RENDER_DPI);
            return tesseract.doOCR(image);
        } catch (Exception e) {
            log.log(Level.WARNING, "OCR failed on page " + (pageIndexZeroBased + 1), e);
            return "";
        }
    }
}
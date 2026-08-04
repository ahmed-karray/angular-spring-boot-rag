package com.example.backend.service;

import com.example.backend.entity.Document;
import com.example.backend.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ExtractionService {

    private static final Logger log = Logger.getLogger(ExtractionService.class.getName());

    private final DocumentRepository documentRepository;
    private final ExtractionRouterService extractionRouterService;
    private final PdfGenerationService pdfGenerationService;

    /**
     * Runs extraction in the background so the upload request returns immediately
     * instead of blocking on OCR. The document is reloaded by id (rather than
     * receiving the detached entity from the caller's transaction) since this
     * runs on a separate thread after the original request's transaction has
     * already completed.
     */
    @Async("extractionTaskExecutor")
    public void extractTextAsync(Long documentId, byte[] fileBytes) {
        Document doc = documentRepository.findById(documentId).orElse(null);
        if (doc == null) {
            log.warning("Document " + documentId + " not found when extraction ran — may have been deleted before extraction completed.");
            return;
        }

        try {
            ExtractionRouterService.RoutedExtraction result = extractionRouterService.extract(fileBytes);

            if ("needs_ocr".equals(result.method())) {
                doc.setExtractionStatus("NEEDS_OCR");
                doc.setExtractionMethod("needs_ocr");
                documentRepository.save(doc);
                return;
            }
            if ("none".equals(result.method())) {
                doc.setExtractionStatus("EMPTY");
                doc.setExtractionMethod("none");
                documentRepository.save(doc);
                return;
            }

            List<String> withoutBoilerplate = HeaderFooterStripper.stripHeadersFooters(result.pages());
            String cleanedText = TextCleaner.clean(String.join("\n\n", withoutBoilerplate));

            doc.setExtractedText(cleanedText);
            doc.setExtractionStatus("SUCCESS");
            doc.setExtractionMethod(result.method());

            String pdfPath = pdfGenerationService.generateAndSavePdf(cleanedText, doc.getFilename());
            doc.setExtractedPdfPath(pdfPath);
            documentRepository.save(doc);
        } catch (Exception e) {
            log.log(Level.WARNING, "Extraction failed for document " + documentId, e);
            doc.setExtractionStatus("FAILED");
            documentRepository.save(doc);
        }
    }
}
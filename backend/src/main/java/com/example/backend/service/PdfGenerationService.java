package com.example.backend.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfGenerationService {

    private static final float MARGIN = 50;
    private static final float FONT_SIZE = 10;
    private static final float LEADING = 14;

    @Value("${app.extracted-pdf.dir}")
    private String extractedPdfDir;

    /**
     * Generates a PDF from text and writes it to disk under app.extracted-pdf.dir.
     * Returns the absolute file path (store THIS in the DB, not the bytes).
     */
    public String generateAndSavePdf(String text, String originalFilename) throws IOException {
        System.out.println(">>> NEW VERSION RUNNING <<<");
        Path dir = Path.of(extractedPdfDir);
        Files.createDirectories(dir);

        String safeName = (originalFilename == null ? "document" : originalFilename)
                .replaceAll("[^a-zA-Z0-9._-]", "_");
        if (!safeName.toLowerCase().endsWith(".pdf")) {
            safeName += ".pdf";
        }

        Path target = dir.resolve(safeName);
        if (Files.exists(target)) {
            String base = safeName.substring(0, safeName.length() - 4); // strip ".pdf"
            int counter = 1;
            do {
                target = dir.resolve(base + "(" + counter + ").pdf");
                counter++;
            } while (Files.exists(target));
        }

        byte[] pdfBytes = generatePdfFromText(text);
        Files.write(target, pdfBytes);

        return target.toAbsolutePath().toString();
    }

    public byte[] generatePdfFromText(String text) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            PDPage firstPage = new PDPage();
            List<String> lines = wrapText(text, font, FONT_SIZE, firstPage.getMediaBox().getWidth() - 2 * MARGIN);
            PDPage page = firstPage;
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.setFont(font, FONT_SIZE);
            content.beginText();
            float y = page.getMediaBox().getHeight() - MARGIN;
            content.newLineAtOffset(MARGIN, y);

            for (String line : lines) {
                if (y <= MARGIN) {
                    content.endText();
                    content.close();

                    page = new PDPage();
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    content.setFont(font, FONT_SIZE);
                    content.beginText();
                    y = page.getMediaBox().getHeight() - MARGIN;
                    content.newLineAtOffset(MARGIN, y);
                }

                content.showText(sanitize(line, font));
                content.newLineAtOffset(0, -LEADING);
                y -= LEADING;
            }

            content.endText();
            content.close();

            java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

    private String sanitize(String line, PDFont font) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            try {
                font.encode(String.valueOf(c));
                sb.append(c);
            } catch (IOException | IllegalArgumentException e) {
                sb.append('?');
            }
        }
        return sb.toString();
    }

    private List<String> wrapText(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        List<String> result = new ArrayList<>();
        for (String paragraph : text.split("\n")) {
            if (paragraph.isBlank()) {
                result.add("");
                continue;
            }
            StringBuilder currentLine = new StringBuilder();
            for (String word : paragraph.split(" ")) {
                String candidate = currentLine.isEmpty() ? word : currentLine + " " + word;
                float width = font.getStringWidth(sanitize(candidate, font)) / 1000 * fontSize;
                if (width > maxWidth && !currentLine.isEmpty()) {
                    result.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine = new StringBuilder(candidate);
                }
            }
            if (!currentLine.isEmpty()) {
                result.add(currentLine.toString());
            }
        }
        return result;
    }
}
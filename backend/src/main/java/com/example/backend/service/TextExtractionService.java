package com.example.backend.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextExtractionService {

    public String extractText(byte[] pdfData) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setLineSeparator("\n");
            return stripper.getText(document);
        }
    }

    public List<String> extractTextByPage(byte[] pdfData) throws IOException {
        List<String> pages = new ArrayList<>();
        try (PDDocument document = Loader.loadPDF(pdfData)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setLineSeparator("\n");
            int totalPages = document.getNumberOfPages();
            for (int i = 1; i <= totalPages; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                pages.add(stripper.getText(document));
            }
        }
        return pages;
    }
}
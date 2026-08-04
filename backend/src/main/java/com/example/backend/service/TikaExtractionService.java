package com.example.backend.service;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TikaExtractionService {

    private static final Pattern PAGE_DIV = Pattern.compile("<div class=\"page\">(.*?)</div>", Pattern.DOTALL);

    /**
     * NOTE: We deliberately do NOT use the org.apache.tika.Tika facade class here.
     * Since Tika 2.4, Tika#parseToString() enforces a hardcoded default max string
     * length of 100,000 characters (a DoS-protection default introduced upstream),
     * which silently truncates large documents with no exception thrown — this is
     * exactly why long PDFs (e.g. multi-page academic papers) were being cut off
     * at precisely 100,000 characters. Using AutoDetectParser + BodyContentHandler(-1)
     * directly bypasses that facade-level cap entirely and gives the same unlimited
     * behavior extractTextByPage() below already relies on.
     */
    public String extractText(byte[] pdfData) throws Exception {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler(-1); // -1 = unlimited character limit
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfData)) {
            parser.parse(inputStream, handler, metadata, context);
        }

        return handler.toString();
    }

    /** Extracts text per page, in document order. Falls back to one big page if boundaries aren't found. */
    public List<String> extractTextByPage(byte[] pdfData) throws Exception {
        AutoDetectParser parser = new AutoDetectParser();
        ToXMLContentHandler xmlHandler = new ToXMLContentHandler();
        BodyContentHandler handler = new BodyContentHandler(xmlHandler);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfData)) {
            parser.parse(inputStream, handler, metadata, context);
        }

        List<String> pages = new ArrayList<>();
        Matcher matcher = PAGE_DIV.matcher(xmlHandler.toString());
        while (matcher.find()) {
            pages.add(unescapeXml(matcher.group(1).replaceAll("<[^>]+>", "")));
        }

        if (pages.isEmpty()) {
            pages.add(extractText(pdfData)); // fallback: no page divs found
        }
        return pages;
    }

    private String unescapeXml(String s) {
        return s.replace("&lt;", "<").replace("&gt;", ">")
                .replace("&quot;", "\"").replace("&apos;", "'")
                .replace("&amp;", "&");
    }
}
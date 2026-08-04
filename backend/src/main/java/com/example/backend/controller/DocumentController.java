package com.example.backend.controller;

import com.example.backend.dto.DocumentResponse;
import com.example.backend.dto.ShareRequest;
import com.example.backend.entity.Department;
import com.example.backend.entity.Document;
import com.example.backend.entity.User;
import com.example.backend.mapper.DocumentMapper;
import com.example.backend.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentMapper documentMapper;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @AuthenticationPrincipal User owner) throws IOException {
        return ResponseEntity.ok(documentService.upload(file, owner, tags));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Department department,
            @RequestParam(required = false) String uploadedBy,
            @RequestParam(required = false) String tag,
            @AuthenticationPrincipal User requester) {
        return ResponseEntity.ok(documentService.search(requester, name, fromDate, toDate, department, uploadedBy, tag));
    }

    @GetMapping("/tags")
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(documentService.getAllTagNames());
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id, @AuthenticationPrincipal User requester) throws IOException {
        Document doc = documentService.getForDownload(id, requester);
        byte[] data = Files.readAllBytes(Path.of(doc.getFilePath()));
        ByteArrayResource resource = new ByteArrayResource(data);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(doc.getFilename())
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentLength(data.length)
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User requester) {
        documentService.delete(id, requester);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/versions", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadNewVersion(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "tags", required = false) List<String> tags,
            @AuthenticationPrincipal User owner) throws IOException {
        return ResponseEntity.ok(documentService.uploadNewVersion(id, file, owner, tags));
    }

    @GetMapping("/{id}/versions")
    public ResponseEntity<List<DocumentResponse>> getVersionHistory(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getVersionHistory(id));
    }

    @PutMapping("/{id}/sharing")
    public ResponseEntity<DocumentResponse> updateSharing(
            @PathVariable Long id,
            @RequestBody ShareRequest request,
            @AuthenticationPrincipal User requester) {
        return ResponseEntity.ok(documentService.updateSharing(id, requester, request));
    }

    @GetMapping("/{id}/text")
    public ResponseEntity<String> getExtractedText(@PathVariable Long id, @AuthenticationPrincipal User requester) {
        Document doc = documentService.getForDownload(id, requester);
        return ResponseEntity.ok(doc.getExtractedText() != null ? doc.getExtractedText() : "");
    }

    @GetMapping("/{id}/extracted-pdf")
    public ResponseEntity<ByteArrayResource> downloadExtractedPdf(@PathVariable Long id, @AuthenticationPrincipal User requester) throws IOException {
        Document doc = documentService.getForDownload(id, requester);

        if (doc.getExtractedPdfPath() == null) {
            throw new IllegalArgumentException("No extracted PDF available for this document");
        }

        byte[] pdfBytes = Files.readAllBytes(Path.of(doc.getExtractedPdfPath()));
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename("extracted_" + doc.getFilename())
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentLength(pdfBytes.length)
                .body(resource);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DocumentResponse> getById(@PathVariable Long id, @AuthenticationPrincipal User requester) {
        Document doc = documentService.getForDownload(id, requester);
        return ResponseEntity.ok(documentMapper.toResponse(doc));
    }
}
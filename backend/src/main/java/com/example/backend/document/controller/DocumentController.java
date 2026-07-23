package com.example.backend.document.controller;

import com.example.backend.document.dto.DocumentResponse;
import com.example.backend.document.dto.ShareRequest;
import com.example.backend.document.entity.Document;
import com.example.backend.document.service.DocumentService;
import com.example.backend.user.entity.Department;
import com.example.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

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
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long id, @AuthenticationPrincipal User requester) {
        Document doc = documentService.getForDownload(id, requester);
        ByteArrayResource resource = new ByteArrayResource(doc.getData());

        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(doc.getFilename())
                .build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .contentLength(doc.getData().length)
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
}
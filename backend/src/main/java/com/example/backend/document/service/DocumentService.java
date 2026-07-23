package com.example.backend.document.service;

import com.example.backend.document.dto.DocumentResponse;
import com.example.backend.document.dto.ShareRequest;
import com.example.backend.document.entity.Document;
import com.example.backend.document.entity.DocumentVisibility;
import com.example.backend.document.entity.Tag;
import com.example.backend.document.mapper.DocumentMapper;
import com.example.backend.document.repository.DocumentRepository;
import com.example.backend.document.repository.DocumentSpecifications;
import com.example.backend.document.repository.TagRepository;
import com.example.backend.user.entity.Department;
import com.example.backend.user.entity.Role;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TagRepository tagRepository;
    private final DocumentMapper documentMapper;
    private final UserRepository userRepository;

    private Set<Tag> resolveTags(List<String> tagNames) {
        Set<Tag> tags = new HashSet<>();
        if (tagNames != null) {
            for (String rawName : tagNames) {
                String name = rawName.trim().toLowerCase();
                if (name.isEmpty()) continue;

                Tag tag = tagRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(name).build()));
                tags.add(tag);
            }
        }
        return tags;
    }

    public DocumentResponse upload(MultipartFile file, User owner, List<String> tagNames) throws IOException {
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        Document doc = Document.builder()
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .uploadedAt(LocalDateTime.now())
                .owner(owner)
                .tags(resolveTags(tagNames))
                .versionNumber(1)
                .isLatest(true)
                .build();

        documentRepository.save(doc);

        // First version is its own root — set after save since we need the generated id
        doc.setRootDocumentId(doc.getId());
        documentRepository.save(doc);

        return documentMapper.toResponse(doc);
    }

    public DocumentResponse uploadNewVersion(Long documentId, MultipartFile file, User owner, List<String> tagNames) throws IOException {
        if (!"application/pdf".equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        Document previousLatest = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        Long rootId = previousLatest.getRootDocumentId() != null
                ? previousLatest.getRootDocumentId()
                : previousLatest.getId();

        previousLatest.setLatest(false);
        documentRepository.save(previousLatest);

        List<Document> existingVersions = documentRepository.findByRootDocumentIdOrderByVersionNumberDesc(rootId);
        int nextVersion = existingVersions.isEmpty() ? 1 : existingVersions.get(0).getVersionNumber() + 1;

        Document newVersion = Document.builder()
                .filename(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .data(file.getBytes())
                .uploadedAt(LocalDateTime.now())
                .owner(owner)
                .tags(resolveTags(tagNames != null && !tagNames.isEmpty() ? tagNames : previousLatest.getTags().stream().map(Tag::getName).toList()))
                .rootDocumentId(rootId)
                .versionNumber(nextVersion)
                .isLatest(true)
                .build();

        documentRepository.save(newVersion);
        return documentMapper.toResponse(newVersion);
    }

    public List<DocumentResponse> getVersionHistory(Long documentId) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        Long rootId = doc.getRootDocumentId() != null ? doc.getRootDocumentId() : doc.getId();

        return documentRepository.findByRootDocumentIdOrderByVersionNumberDesc(rootId).stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    public List<String> getAllTagNames() {
        return tagRepository.findAll().stream().map(Tag::getName).sorted().toList();
    }

    public List<DocumentResponse> search(User requester, String name, LocalDate fromDate, LocalDate toDate, Department department, String uploadedBy, String tag) {
        Specification<Document> spec = DocumentSpecifications.isLatestVersion()
                .and(DocumentSpecifications.visibleToUser(requester));

        if (name != null && !name.isBlank()) {
            spec = spec.and(DocumentSpecifications.filenameContains(name));
        }
        if (fromDate != null) {
            spec = spec.and(DocumentSpecifications.uploadedAfter(fromDate.atStartOfDay()));
        }
        if (toDate != null) {
            spec = spec.and(DocumentSpecifications.uploadedBefore(LocalDateTime.of(toDate, LocalTime.MAX)));
        }
        if (department != null) {
            spec = spec.and(DocumentSpecifications.uploaderDepartmentIs(department));
        }
        if (uploadedBy != null && !uploadedBy.isBlank()) {
            spec = spec.and(DocumentSpecifications.uploadedByUsernameContains(uploadedBy));
        }
        if (tag != null && !tag.isBlank()) {
            spec = spec.and(DocumentSpecifications.hasTag(tag));
        }

        return documentRepository.findAll(spec).stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    public Document getForDownload(Long id, User requester) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        boolean isOwner = doc.getOwner().getId().equals(requester.getId());
        boolean isPublic = doc.getVisibility() == DocumentVisibility.PUBLIC;
        boolean isDeptShared = doc.getVisibility() == DocumentVisibility.DEPARTMENT
                && doc.getOwner().getDepartment() == requester.getDepartment();
        boolean isUserShared = doc.getVisibility() == DocumentVisibility.SPECIFIC_USERS
                && doc.getSharedWithUsers().contains(requester);
        boolean isAdmin = requester.getRole() == Role.ADMIN;

        if (!(isOwner || isPublic || isDeptShared || isUserShared || isAdmin)) {
            throw new IllegalArgumentException("Not authorized to access this document");
        }

        return doc;
    }

    public void delete(Long id, User requester) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        boolean isOwner = doc.getOwner().getId().equals(requester.getId());
        boolean isAdmin = requester.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("Not authorized to delete this document");
        }

        documentRepository.delete(doc);
    }
    public DocumentResponse updateSharing(Long id, User requester, ShareRequest request) {
        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found"));

        boolean isOwner = doc.getOwner().getId().equals(requester.getId());
        boolean isAdmin = requester.getRole() == Role.ADMIN;
        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("Not authorized to change sharing for this document");
        }

        DocumentVisibility visibility;
        try {
            visibility = DocumentVisibility.valueOf(request.visibility());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid visibility value");
        }
        doc.setVisibility(visibility);

        Set<User> sharedUsers = new HashSet<>();
        if (visibility == DocumentVisibility.SPECIFIC_USERS && request.usernames() != null) {
            for (String username : request.usernames()) {
                userRepository.findByUsername(username).ifPresent(sharedUsers::add);
            }
        }
        doc.setSharedWithUsers(sharedUsers);

        documentRepository.save(doc);
        return documentMapper.toResponse(doc);
    }
}
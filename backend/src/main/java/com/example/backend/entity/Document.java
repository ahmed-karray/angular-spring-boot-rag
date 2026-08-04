package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "documents")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 1000)
    private String filePath;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "document_tags",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    @Column(name = "root_document_id")
    private Long rootDocumentId;

    @Column(nullable = false)
    @Builder.Default
    private Integer versionNumber = 1;

    @Column(nullable = false)
    @Builder.Default
    private boolean isLatest = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private DocumentVisibility visibility = DocumentVisibility.PUBLIC;
    @Column(columnDefinition = "TEXT")
    private String extractedText;
    private String extractionMethod; // "tika" | "pdftotext" | "pdfbox" | "none"
    @Column(nullable = false)
    @Builder.Default
    private String extractionStatus = "PENDING";
    @Column(length = 1000)
    private String extractedPdfPath;
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "document_shares",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> sharedWithUsers = new HashSet<>();
}
package com.example.backend.repository;

import com.example.backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {
    List<Document> findByRootDocumentIdOrderByVersionNumberDesc(Long rootDocumentId);
}
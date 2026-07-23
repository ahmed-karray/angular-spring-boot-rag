package com.example.backend.document.repository;

import com.example.backend.document.entity.Document;
import com.example.backend.document.entity.DocumentVisibility;
import com.example.backend.user.entity.Department;
import com.example.backend.user.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DocumentSpecifications {

    public static Specification<Document> belongsToOwner(User owner) {
        return (root, query, cb) -> cb.equal(root.get("owner"), owner);
    }

    public static Specification<Document> filenameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("filename")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Document> uploadedAfter(LocalDateTime date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("uploadedAt"), date);
    }

    public static Specification<Document> uploadedBefore(LocalDateTime date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("uploadedAt"), date);
    }

    public static Specification<Document> uploaderDepartmentIs(Department department) {
        return (root, query, cb) -> cb.equal(root.get("owner").get("department"), department);
    }
    public static Specification<Document> uploadedByUsernameContains(String username) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("owner").get("username")), "%" + username.toLowerCase() + "%");
    }
    public static Specification<Document> hasTag(String tagName) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.like(cb.lower(root.join("tags").get("name")), "%" + tagName.toLowerCase() + "%");
        };
    }
    public static Specification<Document> isLatestVersion() {
        return (root, query, cb) -> cb.isTrue(root.get("isLatest"));
    }
    public static Specification<Document> visibleToUser(User user) {
        return (root, query, cb) -> {
            query.distinct(true);

            var isOwner = cb.equal(root.get("owner"), user);
            var isPublic = cb.equal(root.get("visibility"), DocumentVisibility.PUBLIC);
            var isDeptShared = cb.and(
                    cb.equal(root.get("visibility"), DocumentVisibility.DEPARTMENT),
                    cb.equal(root.get("owner").get("department"), user.getDepartment())
            );
            var isUserShared = cb.and(
                    cb.equal(root.get("visibility"), DocumentVisibility.SPECIFIC_USERS),
                    cb.isMember(user, root.get("sharedWithUsers"))
            );

            return cb.or(isOwner, isPublic, isDeptShared, isUserShared);
        };
    }
}
package com.example.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    /** Writes the given bytes to disk under app.upload.dir and returns the absolute path. */
    public String saveUploadedPdf(byte[] data, String originalFilename) throws IOException {
        Path dir = Path.of(uploadDir);
        Files.createDirectories(dir);

        String safeName = (originalFilename == null ? "document" : originalFilename)
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        Path target = dir.resolve(safeName);
        if (Files.exists(target)) {
            String base = safeName;
            String ext = "";
            int dotIndex = safeName.lastIndexOf('.');
            if (dotIndex > 0) {
                base = safeName.substring(0, dotIndex);
                ext = safeName.substring(dotIndex);
            }
            int counter = 1;
            do {
                target = dir.resolve(base + "(" + counter + ")" + ext);
                counter++;
            } while (Files.exists(target));
        }

        Files.write(target, data);
        return target.toAbsolutePath().toString();
    }

    public byte[] read(String path) throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    public void delete(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException ignored) {
            // best-effort cleanup
        }
    }
}
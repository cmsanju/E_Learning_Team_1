package com.application.controller;
import com.application.DTO.DocumentResponse;
import com.application.model.Document;
import com.application.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
@RestController
@RequestMapping("/api/documents")
@Slf4j
public class DocumentController {
    @Autowired
    private DocumentService documentService;
    @PostMapping("/upload")
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            Document document = documentService.storeDocument(file);
            DocumentResponse response = new DocumentResponse(
                    document.getId(),
                    document.getFileName(),
                    document.getFileType(),
                    document.getUploadDate()
            );
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            log.warn("Validation error: ", e);
            return ResponseEntity.status(e.getStatus())
                    .body(new DocumentResponse(null, null, null, null));
        } catch (IOException e) {
            log.error("Error uploading document: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DocumentResponse(null, null, null, null));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDocument(@PathVariable Long id) {
        try {
            Document document = documentService.getDocument(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(document.getFileType()))
                    .body(document.getData());
        } catch (Exception e) {
            log.error("Error retrieving document: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
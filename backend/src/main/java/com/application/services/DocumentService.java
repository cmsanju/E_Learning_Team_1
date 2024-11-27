package com.application.services;
import com.application.model.Document;
import com.application.repository.DocumentRepository;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
@Slf4j
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Transactional
    public Document storeDocument(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");
        }

        if (file.getSize() > 10_000_000) { // 10MB example limit
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File size exceeds limit");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.matches("^(application|image|text)/.*$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type");
        }
        String fileName = StringUtils.cleanPath(
                Optional.ofNullable(file.getOriginalFilename()).orElse("unnamed_file")
        );
        Document document = new Document();
        document.setFileName(fileName);
        document.setFileType(contentType);
        document.setData(file.getBytes());
        document.setUploadDate(LocalDateTime.now());
        return documentRepository.save(document);
    }
    @Transactional(readOnly = true)
    public Document getDocument(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + id));
    }
}
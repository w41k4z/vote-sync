package internship.project.election.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internship.project.election.service.FileStorageService;

@RequestMapping("/public")
@RestController
public class PublicResourceController {

    private FileStorageService fileStorageService;

    public PublicResourceController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{*filePath}")
    public ResponseEntity<Resource> getFile(@PathVariable String filePath) {
        Path path = fileStorageService.load(filePath);
        try {
            byte[] imageBytes = Files.readAllBytes(path);

            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            // Determine the content type (you might want to use a more dynamic approach)
            String contentType = Files.probeContentType(path);

            return ResponseEntity.ok()
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType)
                            : MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // File not found
        }
    }
}

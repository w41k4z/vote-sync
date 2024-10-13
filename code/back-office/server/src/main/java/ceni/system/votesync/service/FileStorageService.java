package ceni.system.votesync.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ceni.system.votesync.config.Storage;
import ceni.system.votesync.exception.StorageException;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(Storage storageProperties) throws Exception {
        this.rootLocation = Paths.get(storageProperties.getLocation());
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new Exception("Could not create root directory for the storage.", e);
            }
        }
    }

    public void store(MultipartFile multipartFile, String fileName) {
        if (multipartFile.isEmpty()) {
            throw new StorageException("Failed to store empty file. Source: " + multipartFile.getOriginalFilename());
        }
        Path destinationFile = this.rootLocation.resolve(Paths.get(fileName)).normalize().toAbsolutePath();
        try {
            Files.createDirectories(destinationFile.getParent());
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }
}

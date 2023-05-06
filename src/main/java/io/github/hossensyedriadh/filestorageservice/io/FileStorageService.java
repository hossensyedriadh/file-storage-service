package io.github.hossensyedriadh.filestorageservice.io;

import io.github.hossensyedriadh.filestorageservice.model.FileUploadResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStorageService {
    @Value("${spring.servlet.multipart.location}")
    private String storageBaseUrl;

    public FileUploadResponse uploadFile(String fileName, MultipartFile multipartFile) {
        String fullPath = this.storageBaseUrl;

        try {
            File location = new File(fullPath);
            if (!location.exists()) {
                boolean isCreated = location.mkdirs();

                if (!isCreated) {
                    throw new RuntimeException("Failed to create folder");
                }
            }

            fullPath = fullPath.concat(UUID.randomUUID().toString().concat("-").concat(multipartFile.getOriginalFilename() != null
                    ? multipartFile.getOriginalFilename() : multipartFile.getName()));

            File file = new File(fullPath);
            boolean isCreated = file.createNewFile();

            if (isCreated) {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(multipartFile.getBytes());
                fileOutputStream.close();

                if (fileName != null && !fileName.equals("")) {
                    fileName = fileName.concat(".".concat(FilenameUtils.getExtension(fullPath)));
                } else {
                    fileName = multipartFile.getOriginalFilename();
                }

                return new FileUploadResponse(fileName, multipartFile.getContentType(), file.getPath());
            } else {
                throw new RuntimeException("Failed to create file");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteFile(String fullPath) {
        File file = new File(fullPath);
        return file.exists() && file.delete();
    }

    public Resource loadFileAsResource(String fullPath) {
        try {
            Resource resource = new FileUrlResource(fullPath);

            if (resource.exists() && resource.isFile()) {
                if (resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("File is not readable: " + fullPath);
                }
            } else {
                throw new FileNotFoundException("File not found: " + fullPath);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

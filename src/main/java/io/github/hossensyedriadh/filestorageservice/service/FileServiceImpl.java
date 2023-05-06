package io.github.hossensyedriadh.filestorageservice.service;

import io.github.hossensyedriadh.filestorageservice.entity.File;
import io.github.hossensyedriadh.filestorageservice.enumerator.FileSizeUnit;
import io.github.hossensyedriadh.filestorageservice.io.FileStorageService;
import io.github.hossensyedriadh.filestorageservice.model.FileUploadResponse;
import io.github.hossensyedriadh.filestorageservice.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public final class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, FileStorageService fileStorageService) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public List<File> files() {
        return this.fileRepository.findAll(Sort.by(Sort.Order.desc("uploadedOn")));
    }

    @Override
    public Optional<File> file(String id) {
        return this.fileRepository.findById(id);
    }

    @Override
    public File save(File file) {
        MultipartFile multipartFile = file.getMultipartFile();

        FileUploadResponse fileUploadResponse = this.fileStorageService.uploadFile(file.getFileName(), multipartFile);

        file.setFileName(fileUploadResponse.getFileName());
        file.setFileUrl(fileUploadResponse.getUrl());
        file.setContentType(fileUploadResponse.getContentType());

        double size = multipartFile.getSize() / 1024.0;
        if (size > 1024.0) {
            size /= 1024.0;
            file.setFileSizeUnit(FileSizeUnit.MB);
        } else {
            file.setFileSizeUnit(FileSizeUnit.KB);
        }

        file.setFileSize(Double.valueOf(String.format("%.2f", size)));

        return this.fileRepository.saveAndFlush(file);
    }

    @Override
    public void delete(String id) {
        Optional<File> file = this.fileRepository.findById(id);

        if (file.isPresent()) {
            String url = file.get().getFileUrl();
            boolean isDeleted = this.fileStorageService.deleteFile(url);
            if (isDeleted) {
                this.fileRepository.deleteById(id);
            }
        }
    }

    @Override
    public Optional<Resource> fileAsResource(String id) {
        Optional<File> file = this.fileRepository.findById(id);

        if (file.isPresent()) {
            String url = file.get().getFileUrl();
            return Optional.of(this.fileStorageService.loadFileAsResource(url));
        }

        return Optional.empty();
    }
}

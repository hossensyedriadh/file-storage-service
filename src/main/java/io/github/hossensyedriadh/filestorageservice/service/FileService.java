package io.github.hossensyedriadh.filestorageservice.service;

import io.github.hossensyedriadh.filestorageservice.entity.File;
import io.github.hossensyedriadh.filestorageservice.model.FileUploadRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public sealed interface FileService permits FileServiceImpl {
    List<File> files();

    Optional<File> file(String id);

    File save(FileUploadRequest fileUploadRequest, MultipartFile multipartFile);

    void delete(String id);

    Optional<Resource> fileAsResource(String id);
}

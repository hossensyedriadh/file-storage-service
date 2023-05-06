package io.github.hossensyedriadh.filestorageservice.service;

import io.github.hossensyedriadh.filestorageservice.entity.File;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;

public sealed interface FileService permits FileServiceImpl {
    List<File> files();

    Optional<File> file(String id);

    File save(File file);

    void delete(String id);

    Optional<Resource> fileAsResource(String id);
}

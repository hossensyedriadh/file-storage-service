package io.github.hossensyedriadh.filestorageservice.controller;

import io.github.hossensyedriadh.filestorageservice.entity.File;
import io.github.hossensyedriadh.filestorageservice.model.FileUploadRequest;
import io.github.hossensyedriadh.filestorageservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/storage")
public class StorageController {
    private final FileService fileService;

    @Autowired
    public StorageController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ModelAndView files(Model model) {
        List<File> files = this.fileService.files();
        model.addAttribute("files", files);

        return new ModelAndView("storage");
    }

    @PostMapping("/upload")
    public ModelAndView upload(@RequestPart("file") MultipartFile multipartFile) {
        System.out.println(multipartFile.getContentType());
        this.fileService.save(new FileUploadRequest(multipartFile.getOriginalFilename()), multipartFile);
        return new ModelAndView(new RedirectView("/storage"));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable("id") String id) {
        Optional<File> file = this.fileService.file(id);
        Optional<Resource> resource = this.fileService.fileAsResource(id);

        if (file.isPresent() && resource.isPresent()) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.get().getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get().getFileName()
                            + "\"").body(resource.get());
        }

        return ResponseEntity.noContent().build();
    }
}

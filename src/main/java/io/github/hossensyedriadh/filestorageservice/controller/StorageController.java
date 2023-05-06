package io.github.hossensyedriadh.filestorageservice.controller;

import io.github.hossensyedriadh.filestorageservice.entity.File;
import io.github.hossensyedriadh.filestorageservice.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public ModelAndView files(Model model) {
        List<File> files = this.fileService.files();
        model.addAttribute("files", files);
        model.addAttribute("fileEntity", new File());

        return new ModelAndView("storage");
    }

    @PostMapping("/")
    public ModelAndView upload(@ModelAttribute("file") File file) {
        File uploaded = this.fileService.save(file);

        return new ModelAndView(new RedirectView("/storage?h=" + uploaded.getId()));
    }

    @GetMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") String id) {
        this.fileService.delete(id);
        return new ModelAndView(new RedirectView("/storage"));
    }
}

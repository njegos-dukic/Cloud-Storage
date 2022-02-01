package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.sni.dms.model.DetailedFile;
import org.unibl.etf.sni.dms.services.FileSystemService;

import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileSystemController {

    private final FileSystemService fileSystemService;

    @GetMapping
    public List<DetailedFile> getFolderStructure() {
        return fileSystemService.getStructure();
    }
}

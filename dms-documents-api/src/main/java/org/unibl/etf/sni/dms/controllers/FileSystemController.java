package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.sni.dms.model.DMSFolder;
import org.unibl.etf.sni.dms.services.FileSystemService;

@RestController
@RequestMapping("/directory")
@AllArgsConstructor
public class FileSystemController {

    private final FileSystemService fileSystemService;

    @GetMapping
    public DMSFolder getFolderStructure(@RequestParam(required = false) String path) {
        return fileSystemService.populate(path);
    }

    @PostMapping
    public boolean delete(@RequestBody(required = true) @Validated String path) {
        System.out.println("Accepted: " + path);
        return fileSystemService.delete(path);
    }
}

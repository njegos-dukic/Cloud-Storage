package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}

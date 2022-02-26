package org.unibl.etf.sni.dms.controllers;

import lombok.AllArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.sni.dms.model.dtos.DMSFile;
import org.unibl.etf.sni.dms.model.dtos.DMSFolder;
import org.unibl.etf.sni.dms.model.dtos.MoveDTO;
import org.unibl.etf.sni.dms.model.entities.LogEntity;
import org.unibl.etf.sni.dms.services.FileSystemService;
import org.unibl.etf.sni.dms.services.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.time.Instant;

@RestController
@RequestMapping("/directory")
@AllArgsConstructor
public class FileSystemController {

    private final FileSystemService fileSystemService;
    private final LogService logService;

    @GetMapping
    public DMSFolder getFolderStructure(HttpServletRequest request, @RequestParam(required = false) String path) {
        AccessToken accessToken = getKeycloakUser(request);
        String user = accessToken.getPreferredUsername();
        LogEntity logEntity = new LogEntity(0, "INFO", user + " required files and folders", Instant.now());
        logService.insert(logEntity);

        return fileSystemService.populate(user, accessToken);
    }

    @PostMapping("/upload")
    public ResponseEntity<DMSFile> uploadFile(HttpServletRequest request, @PathParam("path") String path, @RequestParam("file") MultipartFile file) {
        AccessToken accessToken = getKeycloakUser(request);
        String user = accessToken.getPreferredUsername();
        LogEntity logEntity = new LogEntity(0, "INFO", user + " uploaded file " + path, Instant.now());
        logService.insert(logEntity);

        DMSFile returnFile = fileSystemService.upload(file, path, accessToken);
        if (returnFile == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(returnFile, HttpStatus.OK);
    }

    @PostMapping("/move")
    public boolean moveFile(HttpServletRequest request, @RequestBody MoveDTO move) {
        AccessToken accessToken = getKeycloakUser(request);
        String user = accessToken.getPreferredUsername();LogEntity logEntity = new LogEntity(0, "INFO", user + " moved file " + move.getSrc() + " to " + move.getDst(), Instant.now());
        logService.insert(logEntity);

        return fileSystemService.move(move, accessToken);
    }

    @PostMapping("/new")
    public DMSFolder createDirectory(HttpServletRequest request, @RequestBody String path) {
        AccessToken accessToken = getKeycloakUser(request);
        String user = accessToken.getPreferredUsername();
        LogEntity logEntity = new LogEntity(0, "INFO", user + " created new directory: " + path, Instant.now());
        logService.insert(logEntity);

        return fileSystemService.createFolder(path, accessToken);
    }

    @PostMapping
    public boolean delete(HttpServletRequest request, @RequestBody(required = true) @Validated String path) {
        AccessToken accessToken = getKeycloakUser(request);
        String user = accessToken.getPreferredUsername();
        LogEntity logEntity = new LogEntity(0, "INFO", user + " deleted " + path, Instant.now());
        logService.insert(logEntity);

        return fileSystemService.delete(user, path, accessToken);
    }

    private AccessToken getKeycloakUser(HttpServletRequest request) {
        KeycloakAuthenticationToken authenticationToken = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) authenticationToken.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        return session.getToken();
    }
}

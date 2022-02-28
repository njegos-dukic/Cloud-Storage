package org.unibl.etf.sni.dms.services;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.sni.dms.exceptions.BadRequestException;
import org.unibl.etf.sni.dms.model.dtos.DMSFile;
import org.unibl.etf.sni.dms.model.dtos.DMSFolder;
import org.unibl.etf.sni.dms.model.dtos.MoveDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class FileSystemService {

    private static final String DMS_ADMIN_ROOT = ".\\..\\";
    private static final String DMS_FS_ROOT = ".\\..\\dms-fs-root\\";
    private static final String DMS_ADMIN_ROLE = "dms-admin";

    public DMSFolder populate(String path, AccessToken accessToken) {

        if (path == null)
            return null;

        File rootFolder = null;
        if (accessToken != null) {
            AccessToken.Access access = accessToken.getRealmAccess();
            Set<String> roles = access.getRoles();
            rootFolder = new File(DMS_FS_ROOT + (roles.contains(DMS_ADMIN_ROLE) ? "" : path));
            if (!rootFolder.exists()) {
                try {
                    Files.createDirectory(Paths.get(rootFolder.getAbsolutePath()));
                } catch (IOException e) {
                    return null;
                }
            }
        }

        else
            rootFolder = new File(path);

        DMSFolder structure = new DMSFolder();
        structure.setName(rootFolder.getName());
        return traverseFolder(structure, rootFolder);
    }

    public boolean delete(String user, String path, AccessToken accessToken) {
        path = path.replace("..", "");
        AccessToken.Access access = accessToken.getRealmAccess();
        Set<String> roles = access.getRoles();
        String file = (roles.contains(DMS_ADMIN_ROLE) ? DMS_ADMIN_ROOT : DMS_FS_ROOT) + path;

        try {
            Files.delete(Paths.get(file));
            return true;
        } catch (DirectoryNotEmptyException e) {
            try {
                FileUtils.deleteDirectory(new File(file));
                return true;
            } catch (IOException ex) {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    public DMSFile upload(MultipartFile multipart, String path, AccessToken accessToken) {
        path = path.replace("..", "");
        AccessToken.Access access = accessToken.getRealmAccess();
        Set<String> roles = access.getRoles();
        String fileName = (roles.contains(DMS_ADMIN_ROLE) ? DMS_ADMIN_ROOT : DMS_FS_ROOT) + path + File.separator + multipart.getOriginalFilename();

        if (new File(fileName).exists())
            throw new BadRequestException("File with that name exists!");

        byte[] fileBytes;
        try {
            fileBytes = multipart.getBytes();
            Files.write(Paths.get(fileName), fileBytes);
            File file = new File(fileName);
            return new DMSFile(file.getName(), Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            return null;
        }
    }

    public boolean move(MoveDTO move, AccessToken accessToken) {
        String src = move.getSrc();
        String dst = move.getDst();

        src = src.replace("..", "");
        dst = dst.replace("..", "");

        try {
            AccessToken.Access access = accessToken.getRealmAccess();
            Set<String> roles = access.getRoles();
            String root = roles.contains(DMS_ADMIN_ROLE) ? DMS_ADMIN_ROOT : DMS_FS_ROOT;
            Files.move(Paths.get(root + src), Paths.get(root + dst));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public DMSFolder createFolder(String path, AccessToken accessToken) {
        path = path.replace("..", "");

        try {
            AccessToken.Access access = accessToken.getRealmAccess();
            Set<String> roles = access.getRoles();
            String root = roles.contains(DMS_ADMIN_ROLE) ? DMS_ADMIN_ROOT : DMS_FS_ROOT;

            Files.createDirectory(Paths.get(root + path));
            File fFolder = new File(root + path);
            DMSFolder folder = new DMSFolder();
            folder.setName(fFolder.getName());
            return folder;
        } catch (IOException e) {
            return null;
        }
    }

    private DMSFolder traverseFolder(DMSFolder structure, File folder) {
        Arrays.stream(Objects.requireNonNull(folder.listFiles())).forEach(f -> {
            try {
                if (f.isFile()) {
                    structure.getFiles().add(new DMSFile(f.getName(), Files.readAllBytes(f.toPath())));
                } else if (f.isDirectory()) {
                    structure.getFolders().add(populate(f.getAbsolutePath(), null));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return structure;
    }
}

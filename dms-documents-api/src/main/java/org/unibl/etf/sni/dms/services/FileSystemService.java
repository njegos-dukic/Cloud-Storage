package org.unibl.etf.sni.dms.services;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.dms.model.DMSFile;
import org.unibl.etf.sni.dms.model.DMSFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FileSystemService {

    public DMSFolder populate(String root) {

        if (root == null)
            root = "C:\\Users\\njego\\Desktop\\sni-projektni-zadatak\\test";

        File rootFolder = new File(root);
        DMSFolder structure = new DMSFolder();
        structure.setName(rootFolder.getName());
        return traverseFolder(structure, rootFolder);
    }

    public boolean delete(String path) {
        String file = "C:\\Users\\njego\\Desktop\\sni-projektni-zadatak\\" + path;

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

    private DMSFolder traverseFolder(DMSFolder structure, File folder) {
        Arrays.stream(Objects.requireNonNull(folder.listFiles())).forEach(f -> {
            try {
                if (f.isFile()) {
                    structure.getFiles().add(new DMSFile(f.getName(), Files.readAllBytes(f.toPath())));
                } else if (f.isDirectory()) {
                    structure.getFolders().add(populate(f.getAbsolutePath()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return structure;
    }
}

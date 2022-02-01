package org.unibl.etf.sni.dms.services;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.springframework.stereotype.Service;
import org.unibl.etf.sni.dms.model.DetailedFile;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileSystemService {

    public List<DetailedFile> getStructure() {
        return FileUtils.listFilesAndDirs(new File("C:\\Users\\njego\\Desktop\\sni-projektni-zadatak\\dms-admin-api"), FileFileFilter.INSTANCE, DirectoryFileFilter.INSTANCE)
                .stream()
                .map(f -> new DetailedFile(f.getParentFile(), f, f.isDirectory() ? "DIR" : "FILE"))
                .collect(Collectors.toList());
    }
}

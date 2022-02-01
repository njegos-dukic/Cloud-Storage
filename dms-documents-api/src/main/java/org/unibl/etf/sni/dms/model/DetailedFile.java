package org.unibl.etf.sni.dms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
public class DetailedFile {

    private File parent;
    private File file;
    private String type;
}

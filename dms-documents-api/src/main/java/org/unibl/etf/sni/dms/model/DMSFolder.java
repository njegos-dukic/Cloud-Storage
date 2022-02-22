package org.unibl.etf.sni.dms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DMSFolder {

    private String name;
    private List<DMSFile> files = new ArrayList<>();
    private List<DMSFolder> folders = new ArrayList<>();
}
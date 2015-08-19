package com.taoswork.tallybook.general.authority.core.authority.resource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TFileRepo {
    final Map<String, TFile> files = new HashMap<String, TFile>();

    public TFileRepo(){
        addTFile("A");
        addTFile("B");
        addTFile("C");
        addTFile("X");
        addTFile("Y");
        addTFile("Z");
    }

    private void addTFile(String tag){
        TFile file = new TFile(tag);
        files.put(tag, file);
    }
}

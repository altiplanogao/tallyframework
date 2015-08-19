package com.taoswork.tallybook.general.authority.core.authority.resource;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class TFile {
    public static final String RESOURCE_TYPE_NAME = "TFile";
    public static final int ACCESS_EXECUTE = 0x02;

    public TFile(String tag){
        this.tag = tag;
    }


    private String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

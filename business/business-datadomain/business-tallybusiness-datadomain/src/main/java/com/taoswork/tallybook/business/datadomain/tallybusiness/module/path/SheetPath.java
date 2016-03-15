package com.taoswork.tallybook.business.datadomain.tallybusiness.module.path;

/**
 * Created by Gao Yuan on 2016/3/14.
 */
public final class SheetPath {
    public final String module;
    public final String sheet;

    public SheetPath(String module, String sheet) {
        this.module = module;
        this.sheet = sheet;
    }

//    public SheetPath(IModule module, ISheet sheet){
//        this.module = module.getFullName();
//        this.sheet = sheet.getFullName();
//    }
}

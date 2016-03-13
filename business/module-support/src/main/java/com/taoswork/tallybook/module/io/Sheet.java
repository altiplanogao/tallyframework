package com.taoswork.tallybook.module.io;

import com.taoswork.tallybook.module.support.ISheet;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public final class Sheet implements ISheet {
    private final String name;
    private final String fullname;
    private final String description;

    public Sheet(ISheet other) {
        this.name = other.getName();
        this.fullname = other.getFullName();
        this.description = other.getDescription();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return fullname;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

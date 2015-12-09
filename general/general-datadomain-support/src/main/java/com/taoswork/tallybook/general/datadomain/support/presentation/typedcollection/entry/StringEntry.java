package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class StringEntry implements IPrimitiveEntry<String> {
    private String entry;

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    @Override
    public String getValue() {
        return entry;
    }

    @Override
    public void setValue(String val) {
        this.entry = val;
    }
}

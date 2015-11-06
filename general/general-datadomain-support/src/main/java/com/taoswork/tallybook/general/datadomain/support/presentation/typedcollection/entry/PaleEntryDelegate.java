package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public class PaleEntryDelegate implements ISimpleEntryDelegate<String> {
    private String entry;

    @Override
    public String getEntryValue() {
        return entry;
    }

    @Override
    public void setEntryValue(String val) {
        this.entry = val;
    }
}

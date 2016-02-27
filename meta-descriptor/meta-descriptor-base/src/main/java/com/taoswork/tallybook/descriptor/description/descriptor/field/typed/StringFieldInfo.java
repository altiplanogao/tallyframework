package com.taoswork.tallybook.descriptor.description.descriptor.field.typed;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class StringFieldInfo extends BasicFieldInfoBase {
    private final int length;

    public StringFieldInfo(String name, String friendlyName, boolean editable, int length) {
        super(name, friendlyName, editable);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

}
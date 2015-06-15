package com.taoswork.tallybook.dynamic.dataservice.server.dto.entity;

import org.springframework.util.StringUtils;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class Property {
    protected String name;
    protected String value;
    protected String displayValue;

    public String getName() {
        return name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Property setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDisplayValue() {
        if(StringUtils.isEmpty(displayValue)){
            return value;
        }
        return displayValue;
    }

    public Property setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
        return this;
    }

    @Override
    public String toString() {
        return name +
                ":" + getDisplayValue();
    }
}

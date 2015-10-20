package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.basic;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet.IFieldFacet;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/1.
 */
public class BooleanFacet implements IFieldFacet {
    private final static String TRUE = "t";
    private final static String FALSE = "f";
    private final Map<String, String> options = new HashMap<String, String>();

    public BooleanFacet() {
        setAsTrueFalse();
    }

    public void setAsTrueFalse() {
        options.put(TRUE, "general.boolean.true");
        options.put(FALSE, "general.boolean.false");
    }

    public void setAsYesNo() {
        options.put(TRUE, "general.boolean.yes");
        options.put(FALSE, "general.boolean.no");
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

    public void setOptionFor(boolean value, String friendlyValue) {
        if (value) {
            options.put(TRUE, friendlyValue);
        } else {
            options.put(FALSE, friendlyValue);
        }
    }

    public Map<String, String> getOptions() {
        return Collections.unmodifiableMap(options);
    }

    public String getOption(boolean value) {
        return options.getOrDefault(value ? TRUE : FALSE, value ? "true" : "false");
    }
}

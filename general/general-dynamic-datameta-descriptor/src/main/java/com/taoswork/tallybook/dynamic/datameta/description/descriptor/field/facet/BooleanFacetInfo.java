package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/1.
 */
public class BooleanFacetInfo implements IFieldFacet {
    private final Map<String, String> options = new HashMap<String, String>();

    public BooleanFacetInfo() {
        options.put("t", "general.boolean.true");
        options.put("f", "general.boolean.false");
    }

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Boolean;
    }

    public void setOptionFor(boolean value, String friendlyValue) {
        if (value) {
            options.put("t", friendlyValue);
        } else {
            options.put("f", friendlyValue);
        }
    }

    public Map<String, String> getOptions() {
        return Collections.unmodifiableMap(options);
    }

    public String getOption(boolean value) {
        return options.getOrDefault(value ? "t" : "f", value ? "true" : "false");
    }
}

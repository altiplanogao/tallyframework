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
    private final static String TRUE = "t";
    private final static String FALSE = "f";

    public BooleanFacetInfo() {
        setAsTrueFalse();
    }

    public void setAsTrueFalse(){
        options.put(TRUE, "general.boolean.true");
        options.put(FALSE, "general.boolean.false");
    }

    public void setAsYesNo(){
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

package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typed;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.BasicFieldInfoBase;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.BooleanFieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.BooleanModel;
import org.apache.commons.collections4.MapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class BooleanFieldInfo extends BasicFieldInfoBase {
    public final static String TRUE = "t";
    public final static String FALSE = "f";

    private final Map<String, String> options = new HashMap<String, String>();

    public BooleanFieldInfo(String name, String friendlyName, boolean editable, BooleanModel booleanModel) {
        super(name, friendlyName, editable);
        switch (booleanModel) {
            case TrueFalse:
                setAsTrueFalse();
                break;
            case YesNo:
                setAsYesNo();
                break;
            default:
                throw new IllegalStateException("Un expected Boolean model");
        }
    }

    public void setAsTrueFalse() {
        options.put(TRUE, "general.boolean.true");
        options.put(FALSE, "general.boolean.false");
    }

    public void setAsYesNo() {
        options.put(TRUE, "general.boolean.yes");
        options.put(FALSE, "general.boolean.no");
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
        return MapUtils.getString(options, value ? TRUE : FALSE, value ? "true" : "false");
    }

}

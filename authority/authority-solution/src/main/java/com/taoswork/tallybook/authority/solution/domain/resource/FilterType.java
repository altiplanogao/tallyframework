package com.taoswork.tallybook.authority.solution.domain.resource;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/25.
 */

@PresentationEnumClass(unknownEnum = "none")
public enum FilterType implements IFriendlyEnum<String> {
    Classified("classification", "Has Classification"),
    None("none", "None");

    public static final String DEFAULT_VAL = "none";

    private final String type;
    private final String friendlyType;

    private static final Map<String, FilterType> typeToEnum = new HashMap<String, FilterType>();

    static {
        for (FilterType _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    FilterType(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static FilterType fromType(String character) {
        return typeToEnum.get(character);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }

}

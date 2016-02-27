package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/25.
 */
@PresentationEnumClass(unknownEnum = "unknown")
public enum Gender implements IFriendlyEnum<String> {
    male("M", "male"),
    female("F", "female"),
    unknown("U", "unknown");

    public static final String DEFAULT_CHAR = "U";

    private final String type;
    private final String friendlyType;

    private static final Map<String, Gender> typeToEnum = new HashMap<String, Gender>();

    static {
        for (Gender _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    Gender(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static Gender fromType(String character) {
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

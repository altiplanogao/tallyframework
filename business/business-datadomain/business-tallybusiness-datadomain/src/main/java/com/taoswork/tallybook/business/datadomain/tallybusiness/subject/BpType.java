package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

@PresentationEnumClass(unknownEnum = "normal")
public enum BpType implements IFriendlyEnum<String> {
    blacklist("b", "blacklist"),
    whitelist("w", "whitelist"),
    normal("n", "normal");

    public static final String DEFAULT_CHAR = "n";

    private final String type;
    private final String friendlyType;

    private static final Map<String, BpType> typeToEnum = new HashMap<String, BpType>();

    static {
        for (BpType _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    BpType(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static BpType fromType(String character) {
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


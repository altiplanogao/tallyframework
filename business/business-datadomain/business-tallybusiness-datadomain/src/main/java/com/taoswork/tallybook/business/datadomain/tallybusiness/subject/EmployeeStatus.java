package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@PresentationEnumClass
public enum EmployeeStatus implements IFriendlyEnum<String> {
    ACTIVE("a", "active"),
    LOCKED("l", "locked");

    private final String type;
    private final String friendlyType;

    private static final Map<String, EmployeeStatus> typeToEnum = new HashMap<String, EmployeeStatus>();

    static {
        for (EmployeeStatus _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    EmployeeStatus(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static EmployeeStatus fromType(String character) {
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

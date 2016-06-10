package com.taoswork.tallybook.business.datadomain.tallymanagement;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/3/1.
 */
public enum ManagementPrivacy implements IFriendlyEnum<String> {
    Public("p", "public"),
    Private("s", "private");

    public static final String DEFAULT_CHAR = "U";

    private final String type;
    private final String friendlyType;

    private static final Map<String, ManagementPrivacy> typeToEnum = new HashMap<String, ManagementPrivacy>();

    static {
        for (ManagementPrivacy _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    ManagementPrivacy(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static ManagementPrivacy fromType(String character) {
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


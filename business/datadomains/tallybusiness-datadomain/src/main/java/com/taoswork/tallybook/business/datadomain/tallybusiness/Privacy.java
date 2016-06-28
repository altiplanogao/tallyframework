package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallycheck.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/3/1.
 */
public enum Privacy implements IFriendlyEnum<String> {
    Public("p", "public"),
    Private("s", "private");

    public static final String DEFAULT_CHAR = "U";

    private final String type;
    private final String friendlyType;

    private static final Map<String, Privacy> typeToEnum = new HashMap<String, Privacy>();

    static {
        for (Privacy _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    Privacy(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static Privacy fromType(String character) {
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

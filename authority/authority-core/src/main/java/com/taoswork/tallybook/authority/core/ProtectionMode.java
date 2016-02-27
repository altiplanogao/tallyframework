package com.taoswork.tallybook.authority.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes resource's protection requirements.
 * If a particular resource requires multiple access permission.
 * "ProtectionMode" define whether all the permission are required or only one of them could be enough.
 *
 * Assuming a resource requires access permission: A and B
 * When the ProtectionMode is FitAll, the user should holds both A and B permission
 * When the ProtectionMode is FitAny, the user should holds at least one of A or B
 */
public enum ProtectionMode {
    FitAny("any", "Any"),
    FitAll("all", "All");

    private final String type;
    private final String friendlyType;

    private static final Map<String, ProtectionMode> typeToEnum = new HashMap<String, ProtectionMode>();
    static {
        for(ProtectionMode _enum : values()){
            typeToEnum.put(_enum.type, _enum);
        }
    }

    ProtectionMode(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static ProtectionMode fromType(String character){
        return typeToEnum.get(character);
    }

    public static String toType(ProtectionMode protectionMode){
        return protectionMode.getType();
    }

    public String getType() {
        return type;
    }

    public String getFriendlyType() {
        return friendlyType;
    }
}
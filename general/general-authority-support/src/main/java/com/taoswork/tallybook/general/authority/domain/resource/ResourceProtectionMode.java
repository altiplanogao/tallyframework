package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationEnum;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/10/20.
 */
@PresentationEnum(unknownEnum = "unknown")
public enum ResourceProtectionMode implements IFriendlyEnum<String> {
    PassAll("all", "fit all"),
    PassAny("any", "fit any");

    public static final String DEFAULT_CHAR = "all";

    private final String type;
    private final String friendlyType;

    private static final Map<String, ResourceProtectionMode> typeToEnum = new HashMap<String, ResourceProtectionMode>();
    static {
        for(ResourceProtectionMode _enum : values()){
            typeToEnum.put(_enum.type, _enum);
        }
    }

    ResourceProtectionMode(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static ResourceProtectionMode fromType(String character){
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

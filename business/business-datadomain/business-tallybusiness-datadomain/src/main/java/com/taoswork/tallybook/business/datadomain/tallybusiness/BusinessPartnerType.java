package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationEnum;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

@PresentationEnum(unknownEnum = "normal")
public enum BusinessPartnerType implements IFriendlyEnum<String> {
    blacklist("b", "blacklist"),
    whitelist("w", "whitelist"),
    normal("n", "normal");

    public static final String DEFAULT_CHAR = "n";

    private final String type;
    private final String friendlyType;

    private static final Map<String, BusinessPartnerType> typeToEnum = new HashMap<String, BusinessPartnerType>();
    static {
        for(BusinessPartnerType _enum : values()){
            typeToEnum.put(_enum.type, _enum);
        }
    }

    BusinessPartnerType(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static BusinessPartnerType fromType(String character){
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


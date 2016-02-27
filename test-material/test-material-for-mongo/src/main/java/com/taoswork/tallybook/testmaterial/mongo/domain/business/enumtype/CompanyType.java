package com.taoswork.tallybook.testmaterial.mongo.domain.business.enumtype;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/31.
 */
@PresentationEnumClass(unknownEnum = "Unknown")
public enum CompanyType implements IFriendlyEnum<String> {
    National("nat", "National"),
    Multinationals("mul", "Multinationals"),
    Private("pri", "Multinationals"),
    Unknown("ukn", "Multinationals");

    private final String type;
    private final String friendlyType;

    private static final Map<String, CompanyType> typeToEnum = new HashMap<String, CompanyType>();

    static {
        for (CompanyType _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    CompanyType(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static CompanyType fromType(String character) {
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

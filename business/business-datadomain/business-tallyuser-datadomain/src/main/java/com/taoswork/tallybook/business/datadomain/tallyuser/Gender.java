package com.taoswork.tallybook.business.datadomain.tallyuser;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/8/25.
 */
public enum Gender implements IFriendlyEnum<Character> {
    Male('m', "male"),
    Female('f', "female");

    private final Character type;
    private final String friendlyType;

    private static final Map<Character, Gender> typeToEnum = new HashMap<Character, Gender>();
    static {
        for(Gender _enum : values()){
            typeToEnum.put(_enum.type, _enum);
        }
    }

    Gender(char type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static Gender fromType(Character character){
        return typeToEnum.get(character);
    }

    @Override
    public Character getType() {
        return type;
    }

    @Override
    public String getFriendlyType() {
        return friendlyType;
    }
}

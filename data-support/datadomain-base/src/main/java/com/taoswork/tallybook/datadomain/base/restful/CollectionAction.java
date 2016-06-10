package com.taoswork.tallybook.datadomain.base.restful;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/19.
 */
public enum CollectionAction implements IFriendlyEnum<String> {
    CREATE("create", "create"),
    READ("read", "read"),
    UPDATE("update", "update"),
    DELETE("delete", "delete"),
    QUERY("query", "query"),

    REORDER("reorder", "reorder"),
    SAVE("save", "save");

    private final String type;
    private final String friendlyType;

    private static final Map<String, CollectionAction> typeToEnum = new HashMap<String, CollectionAction>();

    static {
        for (CollectionAction _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    CollectionAction(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static CollectionAction fromType(String character) {
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

    public static Collection<String> convertToTypes(Collection<CollectionAction> actions, Collection<String> result) {
        if (result == null)
            throw new IllegalArgumentException();
        for (CollectionAction action : actions) {
            result.add(action.getType());
        }
        return result;
    }

}

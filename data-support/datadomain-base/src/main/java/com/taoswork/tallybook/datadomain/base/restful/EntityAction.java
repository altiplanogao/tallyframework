package com.taoswork.tallybook.datadomain.base.restful;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * May refer to {@link EntityActionPaths }
 */
public enum EntityAction implements IFriendlyEnum<String> {
    CREATE("create", "create"),
    READ("read", "read"),
    UPDATE("update", "update"),
    DELETE("delete", "delete"),
    QUERY("query", "query"),

    INFO("info", "info"),
    SELECT("select", "select"),
    TYPEAHEAD("typeahead", "typeahead"),
    SAVE("save", "save");

    private final String type;
    private final String friendlyType;

    private static final Map<String, EntityAction> typeToEnum = new HashMap<String, EntityAction>();

    static {
        for (EntityAction _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    EntityAction(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static EntityAction fromType(String character) {
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

    public static Collection<String> convertToTypes(Collection<EntityAction> actions, Collection<String> result) {
        if (result == null)
            throw new IllegalArgumentException();
        for (EntityAction action : actions) {
            result.add(action.getType());
        }
        return result;
    }
}

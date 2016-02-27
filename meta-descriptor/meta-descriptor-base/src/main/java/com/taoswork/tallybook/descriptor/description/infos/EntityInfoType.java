package com.taoswork.tallybook.descriptor.description.infos;

import com.taoswork.tallybook.descriptor.description.infos.handy.EntityFormInfo;
import com.taoswork.tallybook.descriptor.description.infos.handy.EntityFullInfo;
import com.taoswork.tallybook.descriptor.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.impl.EntityInfoImpl;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public enum EntityInfoType {
    Main(Names.MAIN, EntityInfoImpl.class),
    Full(Names.FULL, EntityFullInfo.class),
    Grid(Names.GRID, EntityGridInfo.class),
    Form(Names.FORM, EntityFormInfo.class);

    public static class Names {
        public final static String MAIN = "main";
        public final static String FULL = "full";
        public final static String GRID = "grid";
        public final static String FORM = "form";
    }

    public final static Set<String> PageSupportedType;
    public final static Set<String> ApiSupportedType;
    private static final Map<String, EntityInfoType> typeToEnum = new HashMap<String, EntityInfoType>();
    private final static Set<String> DefaultHierarchyIncludedType;

    static {
        {
            Set<String> pageSupportedType = new HashSet<String>();
            pageSupportedType.add(Names.FULL);
            pageSupportedType.add(Names.FORM);
            pageSupportedType.add(Names.GRID);
            PageSupportedType = Collections.unmodifiableSet(pageSupportedType);
        }
        {
            Set<String> apiSupportedType = new HashSet<String>();
            apiSupportedType.add(Names.FULL);
            apiSupportedType.add(Names.FORM);
            apiSupportedType.add(Names.GRID);
            ApiSupportedType = Collections.unmodifiableSet(apiSupportedType);
        }
        {
            Set<String> defaultHierarchyIncludedType = new HashSet<String>();
            defaultHierarchyIncludedType.add(Names.FULL);
            defaultHierarchyIncludedType.add(Names.GRID);
            DefaultHierarchyIncludedType = Collections.unmodifiableSet(defaultHierarchyIncludedType);
        }
        for (EntityInfoType _enum : values()) {
            typeToEnum.put(_enum.type, _enum);
        }
    }

    private final String type;
    private final Class<? extends IEntityInfo> entityInfoClass;

    EntityInfoType(String type, Class<? extends IEntityInfo> entityInfoClass) {
        this.type = type;
        this.entityInfoClass = entityInfoClass;
    }

    public static boolean isIncludeHierarchyByDefault(EntityInfoType infoType) {
        return DefaultHierarchyIncludedType.contains(infoType.getType());
    }

    public static EntityInfoType instance(String character) {
        return typeToEnum.get(character);
    }

    public String getType() {
        return type;
    }

    public Class<? extends IEntityInfo> getEntityInfoClass() {
        return entityInfoClass;
    }
}

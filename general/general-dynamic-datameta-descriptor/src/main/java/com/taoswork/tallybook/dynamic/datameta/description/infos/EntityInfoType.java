package com.taoswork.tallybook.dynamic.datameta.description.infos;

import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityFormInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityFullInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.handy.EntityPageGridInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.impl.EntityInfoImpl;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/7/4.
 */
public class EntityInfoType {
    public final static String NAME_OF_MAIN = "main";
    public final static String NAME_OF_FULL = "full";
    public final static String NAME_OF_GRID = "grid";
    public final static String NAME_OF_FORM = "form";

    public final static String NAME_OF_PAGE_GRID = "pageGrid";

    public final static EntityInfoType Main = EntityInfoType.instance(NAME_OF_MAIN);
    public final static EntityInfoType Full = EntityInfoType.instance(NAME_OF_FULL);
    public final static EntityInfoType Grid = EntityInfoType.instance(NAME_OF_GRID);
    public final static EntityInfoType Form = EntityInfoType.instance(NAME_OF_FORM);

    public final static EntityInfoType PageGrid = EntityInfoType.instance(NAME_OF_PAGE_GRID);

    public final static Set<String> PageSupportedType;
    public final static Set<String> ApiSupportedType;
    public final static Map<String, Class<? extends IEntityInfo>> EntityTypeMapping;
    private final static Set<String> DefaultHierarchyIncludedType;

    static {
        {
            Set<String> pageSupportedType = new HashSet<String>();
            pageSupportedType.add(EntityInfoType.NAME_OF_FULL);
            pageSupportedType.add(EntityInfoType.NAME_OF_FORM);
            pageSupportedType.add(EntityInfoType.NAME_OF_GRID);
            pageSupportedType.add(EntityInfoType.NAME_OF_PAGE_GRID);
            PageSupportedType = Collections.unmodifiableSet(pageSupportedType);
        }
        {
            Set<String> apiSupportedType = new HashSet<String>();
            apiSupportedType.add(EntityInfoType.NAME_OF_FULL);
            apiSupportedType.add(EntityInfoType.NAME_OF_FORM);
            apiSupportedType.add(EntityInfoType.NAME_OF_GRID);
            ApiSupportedType = Collections.unmodifiableSet(apiSupportedType);
        }
        {
            Set<String> defaultHierarchyIncludedType = new HashSet<String>();
            defaultHierarchyIncludedType.add(EntityInfoType.NAME_OF_FULL);
            defaultHierarchyIncludedType.add(EntityInfoType.NAME_OF_GRID);
            defaultHierarchyIncludedType.add(EntityInfoType.NAME_OF_PAGE_GRID);
            DefaultHierarchyIncludedType = Collections.unmodifiableSet(defaultHierarchyIncludedType);
        }
        {
            Map<String, Class<? extends IEntityInfo>> entityTypeMapping = new HashMap<String, Class<? extends IEntityInfo>>();
            entityTypeMapping.put(NAME_OF_MAIN, EntityInfoImpl.class);
            entityTypeMapping.put(NAME_OF_FULL, EntityFullInfo.class);
            entityTypeMapping.put(NAME_OF_FORM, EntityFormInfo.class);
            entityTypeMapping.put(NAME_OF_GRID, EntityGridInfo.class);
            entityTypeMapping.put(NAME_OF_PAGE_GRID, EntityPageGridInfo.class);
            EntityTypeMapping = Collections.unmodifiableMap(entityTypeMapping);
        }
    }

    private final String name;


    private EntityInfoType(String name) {
        this.name = name;
    }

    public static boolean isIncludeHierarchyByDefault(EntityInfoType infoType) {
        return DefaultHierarchyIncludedType.contains(infoType.getName());
    }

    public static EntityInfoType instance(String name) {
        return new EntityInfoType(name);
    }

    public String getName() {
        return name;
    }

    public Class<? extends IEntityInfo> infoClass() {
        return EntityTypeMapping.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityInfoType)) return false;

        EntityInfoType that = (EntityInfoType) o;

        return new EqualsBuilder()
            .append(name, that.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(name)
            .toHashCode();
    }
}

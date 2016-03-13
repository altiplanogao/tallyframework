package com.taoswork.tallybook.module.elevator.def.categories;

import com.taoswork.tallybook.module.base.BaseAssetCategory;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public class ElevatorCategory extends BaseAssetCategory {
    public static final String NAME = ElevatorCategory.class.getSimpleName();
    public static final String FULLNAME = ElevatorCategory.class.getName();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getFullName() {
        return FULLNAME;
    }

    @Override
    public Collection<String> getAssetFacets() {
        return null;
    }

    @Override
    public Collection<String> getCapableSheets() {
        return null;
    }
}

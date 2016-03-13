package com.taoswork.tallybook.module.elevator.def.facets;

import com.taoswork.tallybook.module.base.BaseAssetFacet;
import com.taoswork.tallybook.module.elevator.def.categories.ElevatorCategory;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public class Elevator extends BaseAssetFacet {
    public static final String NAME = Elevator.class.getSimpleName();
    public static final String FULLNAME = Elevator.class.getName();

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getFullName() {
        return FULLNAME;
    }

    @Override
    public String getCategory() {
        return ElevatorCategory.FULLNAME;
    }

    @Override
    public String getModel() {
        return null;
    }

    @Override
    public List<String> getCapableSheets() {
        return null;
    }
}

package com.taoswork.tallybook.module.elevator.def.facets;

import com.taoswork.tallybook.module.base.BaseAssetFacet;
import com.taoswork.tallybook.module.elevator.def.categories.EscalatorCategory;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public class Escalator extends BaseAssetFacet {
    public static final String NAME = Escalator.class.getSimpleName();
    public static final String FULLNAME = Escalator.class.getName();

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
        return EscalatorCategory.FULLNAME;
    }

    @Override
    public String getModel() {
        return null;
    }

    @Override
    public Collection<String> getCapableSheets() {
        return null;
    }
}

package com.taoswork.tallybook.module.elevator.def;

import com.taoswork.tallybook.module.base.BaseModule;
import com.taoswork.tallybook.module.elevator.def.categories.ElevatorCategory;
import com.taoswork.tallybook.module.elevator.def.categories.EscalatorCategory;
import com.taoswork.tallybook.module.elevator.def.facets.Elevator;
import com.taoswork.tallybook.module.elevator.def.facets.Escalator;
import com.taoswork.tallybook.module.elevator.def.sheets.AnnualMaintenance;
import com.taoswork.tallybook.module.elevator.def.sheets.MonthMaintenance;
import com.taoswork.tallybook.module.elevator.def.sheets.RegularMaintenance;
import com.taoswork.tallybook.module.elevator.def.sheets.SemiAnnualMaintenance;
import com.taoswork.tallybook.module.support.IAssetCategory;
import com.taoswork.tallybook.module.support.IAssetFacet;
import com.taoswork.tallybook.module.support.ISheet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public class ElevatorModule extends BaseModule {
    public static final String NAME = ElevatorModule.class.getSimpleName();
    public static final String FULLNAME = ElevatorModule.class.getName();

    @Override
    protected Collection<ISheet> makeSheets() {
        List<ISheet> sheets = new ArrayList<ISheet>();
        for (ISheet sheet : new ISheet[]{new AnnualMaintenance(), new MonthMaintenance(), new RegularMaintenance(), new SemiAnnualMaintenance()}) {
            sheets.add(sheet);
        }
        return sheets;
    }

    @Override
    protected Collection<IAssetCategory> makeCategories() {
        List<IAssetCategory> cates = new ArrayList<IAssetCategory>();
        for (IAssetCategory category : new IAssetCategory[]{new ElevatorCategory(), new EscalatorCategory()}) {
            cates.add(category);
        }
        return cates;
    }

    @Override
    protected Collection<IAssetFacet> makeFacets() {
        List<IAssetFacet> facets = new ArrayList<IAssetFacet>();
        for (IAssetFacet facet : new IAssetFacet[]{new Elevator(), new Escalator()}) {
            facets.add(facet);
        }
        return facets;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getFullName() {
        return FULLNAME;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getProducer() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public Date getUpdateDate() {
        return null;
    }

}

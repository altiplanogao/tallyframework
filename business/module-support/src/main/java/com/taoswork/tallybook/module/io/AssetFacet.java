package com.taoswork.tallybook.module.io;

import com.taoswork.tallybook.module.support.IAssetFacet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public final class AssetFacet implements IAssetFacet {
    private final String name;
    private final String fullname;
    private final String description;
    private final String category;
    private final String model;
    private final Collection<String> sheets;

    public AssetFacet(IAssetFacet other) {
        this.name = other.getName();
        this.fullname= other.getFullName();
        this.description = other.getDescription();
        this.category = other.getCategory();
        this.model= other.getModel();
        Collection<String> othersheets = other.getCapableSheets();
        if(othersheets == null){
            this.sheets = null;
        }else {
            List<String> copy = new ArrayList<String>(othersheets);
            this.sheets = Collections.unmodifiableCollection(copy);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullName() {
        return fullname;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public Collection<String> getCapableSheets() {
        return sheets;
    }
}

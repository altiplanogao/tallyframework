package com.taoswork.tallybook.module.io;

import com.taoswork.tallybook.module.support.IAssetCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public final class AssetCategory implements IAssetCategory {
    private final String name;
    private final String fullname;
    private final String description;
    private final Collection<String> facets;
    private final Collection<String> sheets;

    public AssetCategory(IAssetCategory other) {
        this.name = other.getName();
        this.fullname = other.getFullName();
        this.description = other.getDescription();
        Collection<String> otherfacets = other.getAssetFacets();
        if (otherfacets == null) {
            this.facets = null;
        } else {
            List<String> copy = new ArrayList<String>(otherfacets);
            this.facets = Collections.unmodifiableCollection(copy);
        }

        Collection<String> othersheets = other.getCapableSheets();
        if (othersheets == null) {
            this.sheets = null;
        } else {
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
    public Collection<String> getAssetFacets() {
        return facets;
    }

    @Override
    public Collection<String> getCapableSheets() {
        return sheets;
    }
}

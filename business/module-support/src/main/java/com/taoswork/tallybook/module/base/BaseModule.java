package com.taoswork.tallybook.module.base;

import com.taoswork.tallybook.module.io.AssetCategory;
import com.taoswork.tallybook.module.io.AssetFacet;
import com.taoswork.tallybook.module.io.IModule;
import com.taoswork.tallybook.module.io.Sheet;
import com.taoswork.tallybook.module.support.IAssetCategory;
import com.taoswork.tallybook.module.support.IAssetFacet;
import com.taoswork.tallybook.module.support.ISheet;

import java.util.*;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public abstract class BaseModule implements IModule {

    private final Map<String, Sheet> sheetsCache;
    private final Map<String, AssetCategory> categoryCache;
    private final Map<String, AssetFacet> facetCache;

    public BaseModule() {
        {
            Collection<ISheet> sheets = makeSheets();
            if (sheets == null || sheets.isEmpty()) {
                sheetsCache = null;
            } else {
                Map<String, Sheet> localSheets = new HashMap<String, Sheet>();
                for (ISheet isheet : sheets) {
                    final Sheet sheet = (isheet instanceof Sheet) ?
                            (Sheet) isheet : new Sheet(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                sheetsCache = Collections.unmodifiableMap(localSheets);
            }
        }
        {
            Collection<IAssetCategory> sheets = makeCategories();
            if (sheets == null || sheets.isEmpty()) {
                categoryCache = null;
            } else {
                Map<String, AssetCategory> localSheets = new HashMap<String, AssetCategory>();
                for (IAssetCategory isheet : sheets) {
                    final AssetCategory sheet = (isheet instanceof AssetCategory) ?
                            (AssetCategory) isheet : new AssetCategory(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                categoryCache = Collections.unmodifiableMap(localSheets);
            }
        }
        {
            Collection<IAssetFacet> sheets = makeFacets();
            if (sheets == null || sheets.isEmpty()) {
                facetCache = null;
            } else {
                Map<String, AssetFacet> localSheets = new HashMap<String, AssetFacet>();
                for (IAssetFacet isheet : sheets) {
                    final AssetFacet sheet = (isheet instanceof AssetFacet) ?
                            (AssetFacet) isheet : new AssetFacet(isheet);
                    localSheets.put(sheet.getFullName(), sheet);
                }
                facetCache = Collections.unmodifiableMap(localSheets);
            }
        }
    }

    protected abstract Collection<ISheet> makeSheets();
    protected abstract Collection<IAssetCategory> makeCategories();
    protected abstract Collection<IAssetFacet> makeFacets();

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getFullName() {
        return this.getClass().getName();
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

    @Override
    public Collection<String> getCategories() {
        return categoryCache.keySet();
    }

    @Override
    public AssetCategory getCategory(String category) {
        return categoryCache.get(category);
    }

    @Override
    public Collection<String> getSheets() {
        return sheetsCache.keySet();
    }

    @Override
    public Sheet getSheet(String sheet) {
        return sheetsCache.get(sheet);
    }

    @Override
    public AssetFacet getFacet(String facet) {
        return facetCache.get(facet);
    }
}

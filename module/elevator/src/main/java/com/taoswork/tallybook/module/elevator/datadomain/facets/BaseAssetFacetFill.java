package com.taoswork.tallybook.module.elevator.datadomain.facets;

import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallybook.module.fill.AssetFacetFill;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public abstract class BaseAssetFacetFill
        extends AbstractDocument
        implements AssetFacetFill {
    private String assetId;

    @Override
    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}

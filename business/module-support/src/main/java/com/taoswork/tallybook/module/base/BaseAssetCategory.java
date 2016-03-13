package com.taoswork.tallybook.module.base;

import com.taoswork.tallybook.module.support.IAssetCategory;


/**
 * Created by Gao Yuan on 2016/3/10.
 */
public abstract class BaseAssetCategory implements IAssetCategory{
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
}

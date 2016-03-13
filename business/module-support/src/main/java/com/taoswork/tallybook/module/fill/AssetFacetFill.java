package com.taoswork.tallybook.module.fill;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public interface AssetFacetFill extends Serializable {
    //String getModule();
//    private String module;
//    private String category;
//    private String model;
////    private ObjectId assetId;
    String getAssetId();
}

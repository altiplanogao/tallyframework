package com.taoswork.tallybook.business.datadomain.tallybusiness.subject;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
@Embedded
public final class AssetFacet {
    private String module;
//    private String category;
//    private String model;
    private String facet;
    private String facetFillId;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }

    public String getFacetFillId() {
        return facetFillId;
    }

    public void setFacetFillId(String facetFillId) {
        this.facetFillId = facetFillId;
    }
}

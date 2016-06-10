package com.taoswork.tallybook.business.datadomain.tallybusiness.module.path;

/**
 * Created by Gao Yuan on 2016/3/14.
 */
public final class FacetPath {
    public final String module;
    public final String facet;

    public FacetPath(String module, String facet) {
        this.module = module;
        this.facet = facet;
    }
//
//    public FacetPath(IModule module, IAssetFacet facet) {
//        this.module = module.getFullName();
//        this.facet = module.getFullName();
//    }
}

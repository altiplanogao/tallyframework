package com.taoswork.tallybook.module.support;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public interface IAssetFacet extends Serializable{

    String getName();

    String getFullName();

    String getDescription();

    String getCategory();

    String getModel();

    Collection<String> getCapableSheets();
}

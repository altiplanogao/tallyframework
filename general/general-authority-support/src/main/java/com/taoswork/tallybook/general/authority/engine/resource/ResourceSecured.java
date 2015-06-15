package com.taoswork.tallybook.general.authority.engine.resource;

import com.taoswork.tallybook.general.authority.domain.authority.access.SecureMode;
import com.taoswork.tallybook.general.authority.engine.filter.IResourceFilter;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class ResourceSecured {
    String resourceType;
    SecureMode secureMode;
    List<IResourceFilter> matchingResourceFilters;
    List<IResourceFilter> unMatchingResourceFilters;
}

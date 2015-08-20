package com.taoswork.tallybook.general.authority.mockup.resource.filters.doc;

import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocTagFilter implements IResourceFilter {
    private final String tag;

    public DocTagFilter(String tag){
        this.tag = tag;
    }

    @Override
    public String getCode() {
        return tag;
    }

    @Override
    public String getFriendlyName() {
        return "Filter By Tag: " + tag;
    }

    @Override
    public boolean isMaster() {
        return false;
    }

    @Override
    public boolean isMatch(IResourceInstance instance) {
        GuardedDoc doc = ((GuardedDocInstance)instance).getDomainObject();
        if(doc == null)
            return false;
        return doc.hasTag(tag);
    }

    @Override
    public String unMatchQueryInterrupter() {
        return null;
    }
}

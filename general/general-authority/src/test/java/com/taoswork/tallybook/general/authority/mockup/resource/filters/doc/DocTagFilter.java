package com.taoswork.tallybook.general.authority.mockup.resource.filters.doc;

import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocTagFilter implements IResourceFilter {
    private final String tag;

    public DocTagFilter(String tag) {
        this.tag = tag;
    }

    @Override
    public String getCode() {
        return tag;
    }

    @Override
    public boolean isMatch(Object instance) {
        GuardedDoc doc = ((GuardedDocInstance) instance).getDomainObject();
        if (doc == null)
            return false;
        return doc.hasTag(tag);
    }

    @Override
    public String unMatchQueryInterrupter() {
        return null;
    }
}

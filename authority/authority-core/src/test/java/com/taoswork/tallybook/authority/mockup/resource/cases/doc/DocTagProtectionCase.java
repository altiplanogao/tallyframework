package com.taoswork.tallybook.authority.mockup.resource.cases.doc;

import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.authority.mockup.resource.domain.GuardedDoc;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocTagProtectionCase implements IKProtectionCase {
    private final String tag;

    public DocTagProtectionCase(String tag) {
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
}

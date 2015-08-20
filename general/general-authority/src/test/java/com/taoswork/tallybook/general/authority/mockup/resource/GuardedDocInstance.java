package com.taoswork.tallybook.general.authority.mockup.resource;

import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class GuardedDocInstance implements IResourceInstance {
    private final GuardedDoc domainObject;
    public GuardedDocInstance(GuardedDoc doc){
        domainObject = doc;
    }
    @Override
    public String getResourceEntity() {
        return TypesEnums.DOC;
    }

    public GuardedDoc getDomainObject() {
        return domainObject;
    }

    @Override
    public String toString() {
        return "" +domainObject;

    }
}

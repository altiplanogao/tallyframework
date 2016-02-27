package com.taoswork.tallybook.authority.mockup.resource;

import com.taoswork.tallybook.authority.mockup.resource.domain.GuardedDoc;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class GuardedDocInstance implements Serializable {
    private final GuardedDoc domainObject;

    public GuardedDocInstance(GuardedDoc doc) {
        domainObject = doc;
    }

    public GuardedDoc getDomainObject() {
        return domainObject;
    }

    @Override
    public String toString() {
        return "" + domainObject;
    }
}

package com.taoswork.tallybook.authority.mockup.resource.domain;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public abstract class GuardedResource {
    private final String name;

    public GuardedResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

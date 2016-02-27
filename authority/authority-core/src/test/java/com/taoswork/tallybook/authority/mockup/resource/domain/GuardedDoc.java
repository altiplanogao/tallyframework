package com.taoswork.tallybook.authority.mockup.resource.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class GuardedDoc extends GuardedResource {
    private Set<String> tags = new HashSet<String>();

    public GuardedDoc(String name, Collection<String> tags) {
        super(name);
        if (tags != null) {
            for (String tag : tags) {
                this.tags.add(tag);
            }
        }
    }

    public GuardedDoc addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(tag + ',');
        }
        return sb.toString();
    }
}

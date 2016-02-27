package com.taoswork.tallybook.authority.solution.engine.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.solution.engine.filter.IFilter;
import com.taoswork.tallybook.general.solution.exception.UnexpectedException;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class ResourceProtectionCase implements IKProtectionCase {
    private final String code;
    private IFilter filter;

    public ResourceProtectionCase(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public IFilter getFilter() {
        return filter;
    }

    public void setFilter(IFilter filter) {
        this.filter = filter;
    }

    @Override
    public boolean isMatch(Object instance) {
        if (filter == null)
            throw new UnexpectedException();
        return filter.isMatch(instance);
    }
}

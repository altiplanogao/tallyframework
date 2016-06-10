package com.taoswork.tallybook.datadomain.base.entity.valuecopier;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/11/11.
 * <p>
 * Implement this interface for fast entity copy
 */
public interface IEntityCopier {

    boolean allHandled();

    Collection<String> handledFields();

    void copy(Object src, Object target);
}

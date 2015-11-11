package com.taoswork.tallybook.general.datadomain.support.entity.valuecopier;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/11/11.
 *
 * Implement this interface for fast entity copy
 */
public interface IEntityValueCopier {

    boolean allHandled();

    Collection<String> handledFields();

    void copy(Object src, Object target);
}

package com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public interface IPrimitiveEntry<T> {
    T getValue();

    void setValue(T val);
}

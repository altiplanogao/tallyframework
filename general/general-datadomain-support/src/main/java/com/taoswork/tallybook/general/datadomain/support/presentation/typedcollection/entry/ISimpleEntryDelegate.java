package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry;

/**
 * Created by Gao Yuan on 2015/11/6.
 */
public interface ISimpleEntryDelegate<T> {
    T getEntryValue();
    void setEntryValue(T val);
}

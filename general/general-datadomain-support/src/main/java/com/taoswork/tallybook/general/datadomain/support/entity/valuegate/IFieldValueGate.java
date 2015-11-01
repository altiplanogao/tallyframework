package com.taoswork.tallybook.general.datadomain.support.entity.valuegate;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface IFieldValueGate {
    Object store(Object val, Object oldVal);
}

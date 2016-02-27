package com.taoswork.tallybook.datadomain.base.entity.valuegate;

/**
 * Created by Gao Yuan on 2015/11/1.
 */
public interface IFieldGate {
    Object store(Object val, Object oldVal);
}

package com.taoswork.tallybook.testframework.general;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public interface Converter<F,T> {
    T convert(F from);
}

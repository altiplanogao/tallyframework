package com.taoswork.tallybook.testmaterial.general.utils;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public interface Converter<F, T> {
    T convert(F from);
}

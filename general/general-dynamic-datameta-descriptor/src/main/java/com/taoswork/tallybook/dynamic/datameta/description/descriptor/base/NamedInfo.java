package com.taoswork.tallybook.dynamic.datameta.description.descriptor.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedInfo extends Serializable {

    String getName();

    String getFriendlyName();
}

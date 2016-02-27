package com.taoswork.tallybook.descriptor.description.descriptor.base;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedInfo extends Serializable {

    String getName();

    String getFriendlyName();
}

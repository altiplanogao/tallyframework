package com.taoswork.tallybook.module.support;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
public interface ISheet extends Serializable {

    String getName();

    String getFullName();

    String getDescription();

}

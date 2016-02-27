package com.taoswork.tallybook.descriptor.jpa.service;

import com.taoswork.tallybook.descriptor.jpa.metadata.processor.JpaClassProcessor;
import com.taoswork.tallybook.descriptor.service.impl.BaseMetaServiceImpl;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class JpaMetaServiceImpl extends BaseMetaServiceImpl {
    public JpaMetaServiceImpl() {
        super(new JpaClassProcessor());
    }
}
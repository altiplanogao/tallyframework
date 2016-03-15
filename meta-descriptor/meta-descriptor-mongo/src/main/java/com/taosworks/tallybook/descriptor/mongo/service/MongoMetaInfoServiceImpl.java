package com.taosworks.tallybook.descriptor.mongo.service;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2IPool;
import com.taoswork.tallybook.descriptor.service.impl.BaseMetaInfoServiceImpl;
import com.taosworks.tallybook.descriptor.mongo.description.builder.m2i.basic.ObjectIdFM2I;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
public class MongoMetaInfoServiceImpl extends BaseMetaInfoServiceImpl {
    @Override
    protected FM2IPool createFM2IPool() {
        FM2IPool pool = new FM2IPool(){
            @Override
            protected void addFM2Is() {
                super.addFM2Is();
                addFM2I(new ObjectIdFM2I());
            }
        };
        return pool;
    }
}

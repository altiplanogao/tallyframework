package com.taoswork.tallybook.dataservice.server.service;

import com.taoswork.tallybook.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallybook.dataservice.mongo.core.convertors.ObjectIdConverter;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public class FrontEndEntityTranslator extends InputEntityTranslator {
    @Override
    protected ConvertUtilsBean2 makeConvertUtils() {
        ConvertUtilsBean2 convertUtilsBean2 = super.makeConvertUtils();
        convertUtilsBean2.register(new ObjectIdConverter(), ObjectId.class);
        return convertUtilsBean2;
    }
}

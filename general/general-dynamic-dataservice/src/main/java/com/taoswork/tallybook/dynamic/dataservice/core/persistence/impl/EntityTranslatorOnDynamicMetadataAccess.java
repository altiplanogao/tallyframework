package com.taoswork.tallybook.dynamic.dataservice.core.persistence.impl;

import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataio.in.translator.EntityTranslator;
import com.taoswork.tallybook.dynamic.dataio.in.translator.TranslateException;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
abstract class EntityTranslatorOnDynamicMetadataAccess extends EntityTranslator {

    public Persistable convert(Entity source, String id) throws ServiceException {
        try {
            return super.translate(source, id);
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
    }
}

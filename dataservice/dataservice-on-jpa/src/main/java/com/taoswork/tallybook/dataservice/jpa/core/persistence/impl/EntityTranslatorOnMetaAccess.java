package com.taoswork.tallybook.dataservice.jpa.core.persistence.impl;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallybook.descriptor.dataio.in.translator.TranslateException;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
abstract class EntityTranslatorOnMetaAccess extends EntityTranslator {

    public Persistable convert(Entity source, String id) throws ServiceException {
        try {
            return super.translate(source, id);
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
    }
}

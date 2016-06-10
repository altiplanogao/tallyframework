package com.taoswork.tallybook.dataservice.core.persistence;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallybook.descriptor.dataio.in.translator.TranslateException;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class InputEntityTranslator extends EntityTranslator {

    public Persistable convert(final IClassMetaAccess cmAccess, Entity source, String id) throws ServiceException {
        try {
            return super.translate(cmAccess, source, id);
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
    }
}

package com.taoswork.tallybook.descriptor.dataio.in.translator;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public interface IEntityTranslator {
    Persistable translate(IClassMetaAccess cmAccess, Entity source, String id) throws TranslateException;
}

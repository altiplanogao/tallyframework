package com.taoswork.tallybook.descriptor.dataio.copier;

import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class CopierContext {
    public final IClassMetaAccess classMetaAccess;
    public final ExternalReference externalReference;

    public CopierContext(IClassMetaAccess classMetaAccess, ExternalReference externalReference) {
        this.classMetaAccess = classMetaAccess;
        this.externalReference = externalReference;
    }
}

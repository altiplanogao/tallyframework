package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper;

import com.taoswork.tallybook.dynamic.dataio.reference.ExternalReference;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadataAccess;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class CopierContext {
    public final IClassMetadataAccess classMetadataAccess;
    public final ExternalReference externalReference;

    public CopierContext(IClassMetadataAccess classMetadataAccess, ExternalReference externalReference) {
        this.classMetadataAccess = classMetadataAccess;
        this.externalReference = externalReference;
    }
}

package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuecoper;

import com.taoswork.tallybook.dynamic.dataservice.core.dataio.ExternalReference;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class CopierContext {
    public final DynamicEntityMetadataAccess dynamicEntityMetadataAccess;
    public final ExternalReference externalReference;

    public CopierContext(DynamicEntityMetadataAccess dynamicEntityMetadataAccess, ExternalReference externalReference) {
        this.dynamicEntityMetadataAccess = dynamicEntityMetadataAccess;
        this.externalReference = externalReference;
    }
}

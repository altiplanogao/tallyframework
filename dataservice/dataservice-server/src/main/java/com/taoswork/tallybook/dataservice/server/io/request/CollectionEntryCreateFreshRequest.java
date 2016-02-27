package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.server.io.request.parameter.CollectionEntryTypeParameter;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;

import java.net.URI;

public class CollectionEntryCreateFreshRequest extends EntityRequest {
    public final CollectionEntryTypeParameter collectionEntryTypeParameter;

    public CollectionEntryCreateFreshRequest(EntityTypeParameter entityTypeParam, URI fullUri, CollectionEntryTypeParameter collectionEntryTypeParameter) {
        super(entityTypeParam, fullUri);
        this.collectionEntryTypeParameter = collectionEntryTypeParameter;
    }

    public Class getEntryPresentationClass() {
        return collectionEntryTypeParameter.getType();
    }
}

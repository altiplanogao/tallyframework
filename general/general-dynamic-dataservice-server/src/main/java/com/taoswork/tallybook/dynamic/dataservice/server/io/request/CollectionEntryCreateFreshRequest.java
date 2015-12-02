package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

public class CollectionEntryCreateFreshRequest extends EntityRequest {
    public final String collectionField;
    public CollectionEntryCreateFreshRequest(String collectionField) {
        this.collectionField = collectionField;
    }
}

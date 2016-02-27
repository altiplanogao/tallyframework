package com.taoswork.tallybook.descriptor.dataio.reference;

import java.util.Collection;
import java.util.Map;

public interface IEntityRecordsFetcher {
    Map<Object,Object> fetch(Class entityType, Collection<Object> ids) throws EntityFetchException;
}

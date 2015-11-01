package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IFieldValueGate;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class FieldValueGateManager {

    private final static FakeValueGate fakeValueGate = new FakeValueGate();
    private final ConcurrentMap<String, IFieldValueGate> valueGateCache = new ConcurrentHashMap<String, IFieldValueGate>();

    private IFieldValueGate getValueGate(String gateName) {
        IFieldValueGate gate = valueGateCache.computeIfAbsent(gateName, new Function<String, IFieldValueGate>() {
            @Override
            public IFieldValueGate apply(String s) {
                try {
                    IFieldValueGate g = (IFieldValueGate) Class.forName(s).newInstance();
                    return g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fakeValueGate;
            }
        });
        if (gate == fakeValueGate)
            return null;
        return gate;
    }

    IFieldValueGate getValueGate(Class<? extends IFieldValueGate> cls){
        if(cls == null)
            return null;
        return this.getValueGate(cls.getName());
    }

    private static class FakeValueGate implements IFieldValueGate {
        @Override
        public Object store(Object val, Object oldVal) {
            return null;
        }
    }
}

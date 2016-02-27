package com.taoswork.tallybook.datadomain.base.entity.valuegate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class FieldGatePool {

    private final static FakeGate fakeValueGate = new FakeGate();
    private final ConcurrentMap<String, IFieldGate> valueGateCache = new ConcurrentHashMap<String, IFieldGate>();

    private IFieldGate getValueGate(String gateName) {
        IFieldGate gate = valueGateCache.computeIfAbsent(gateName, new Function<String, IFieldGate>() {
            @Override
            public IFieldGate apply(String s) {
                try {
                    IFieldGate g = (IFieldGate) Class.forName(s).newInstance();
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

    public IFieldGate getValueGate(Class<? extends IFieldGate> cls) {
        if (cls == null)
            return null;
        return this.getValueGate(cls.getName());
    }

    private static class FakeGate implements IFieldGate {
        @Override
        public Object store(Object val, Object oldVal) {
            return null;
        }
    }
}

package com.taoswork.tallybook.datadomain.base.entity.valuegate;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2016/2/18.
 */
public class EntityGatePool {
    private final static FakeGate fakeValueGate = new FakeGate();
    private final ConcurrentMap<String, IEntityGate> valueGateCache = new ConcurrentHashMap<String, IEntityGate>();

    protected IEntityGate getValueGate(String gateName) {
        IEntityGate gate = valueGateCache.computeIfAbsent(gateName, new Function<String, IEntityGate>() {
            @Override
            public IEntityGate apply(String s) {
                try {
                    IEntityGate g = (IEntityGate) Class.forName(s).newInstance();
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

    private static class FakeGate implements IEntityGate {
        @Override
        public void store(Persistable entity, Persistable oldEntity) {
            throw new IllegalAccessError();
        }

        @Override
        public void fetch(Persistable entity) {
            throw new IllegalAccessError();
        }
    }
}

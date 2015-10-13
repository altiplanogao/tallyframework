package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class EntityValueGateManager {
    private static class FakeValueGate implements IEntityValueGate{
        @Override
        public void deposit(Persistable entity, Persistable oldEntity) {}

        @Override
        public void withdraw(Persistable entity) {}
    }
    private final static FakeValueGate fakeValueGate = new FakeValueGate();
    private final ConcurrentMap<String, IEntityValueGate> valueGateCache = new ConcurrentHashMap<String, IEntityValueGate>();
    private IEntityValueGate getValueGate(String gateName){
        IEntityValueGate gate = valueGateCache.computeIfAbsent(gateName, new Function<String, IEntityValueGate>() {
            @Override
            public IEntityValueGate apply(String s) {
                try {
                    IEntityValueGate g = (IEntityValueGate) Class.forName(s).newInstance();
                    return g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fakeValueGate;
            }
        });
        if(gate == fakeValueGate)
            return null;
        return gate;
    }

    public void deposit(ClassMetadata classMetadata, Persistable entity, Persistable oldEntity){
        Collection<String> gateNames = classMetadata.getValueGates();
        for(String gateName : gateNames){
            IEntityValueGate gate = getValueGate(gateName);
            gate.deposit(entity, oldEntity);
        }
    }

    public void withdraw(ClassMetadata classMetadata, Persistable entity){
        Collection<String> gateNames = classMetadata.getValueGates();
        for(String gateName : gateNames){
            IEntityValueGate gate = getValueGate(gateName);
            gate.withdraw(entity);
        }
    }
}

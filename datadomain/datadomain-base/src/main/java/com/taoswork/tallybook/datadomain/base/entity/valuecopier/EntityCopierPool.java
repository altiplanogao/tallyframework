package com.taoswork.tallybook.datadomain.base.entity.valuecopier;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class EntityCopierPool {

    private final static FakeCopier FAKE_VALUE_COPIER = new FakeCopier();
    private final ConcurrentMap<String, IEntityCopier> valueCopierCache = new ConcurrentHashMap<String, IEntityCopier>();

    public IEntityCopier getValueCopier(String copierName) {
        if(StringUtils.isEmpty(copierName))
            return null;
        IEntityCopier copier = valueCopierCache.computeIfAbsent(copierName, new Function<String, IEntityCopier>() {
            @Override
            public IEntityCopier apply(String s) {
                try {
                    IEntityCopier g = (IEntityCopier) Class.forName(s).newInstance();
                    return g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FAKE_VALUE_COPIER;
            }
        });
        if (copier == FAKE_VALUE_COPIER)
            return null;
        return copier;
    }

    private static class FakeCopier implements IEntityCopier {
        @Override
        public boolean allHandled() {
            throw new IllegalAccessError();
        }

        @Override
        public Collection<String> handledFields() {
            throw new IllegalAccessError();
        }

        @Override
        public void copy(Object src, Object target) {
            throw new IllegalAccessError();
        }
    }
}

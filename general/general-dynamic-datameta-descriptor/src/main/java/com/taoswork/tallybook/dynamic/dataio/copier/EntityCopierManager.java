package com.taoswork.tallybook.dynamic.dataio.copier;

import com.taoswork.tallybook.general.datadomain.support.entity.valuecopier.IEntityValueCopier;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class EntityCopierManager {

    private final static FakeValueCopier FAKE_VALUE_COPIER = new FakeValueCopier();
    private final ConcurrentMap<String, IEntityValueCopier> valueGateCache = new ConcurrentHashMap<String, IEntityValueCopier>();

    public IEntityValueCopier getValueCopier(String gateName) {
        if(StringUtils.isEmpty(gateName))
            return null;
        IEntityValueCopier gate = valueGateCache.computeIfAbsent(gateName, new Function<String, IEntityValueCopier>() {
            @Override
            public IEntityValueCopier apply(String s) {
                try {
                    IEntityValueCopier g = (IEntityValueCopier) Class.forName(s).newInstance();
                    return g;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return FAKE_VALUE_COPIER;
            }
        });
        if (gate == FAKE_VALUE_COPIER)
            return null;
        return gate;
    }

    private static class FakeValueCopier implements IEntityValueCopier {
        @Override
        public boolean allHandled() {
            return false;
        }

        @Override
        public Collection<String> handledFields() {
            return null;
        }

        @Override
        public void copy(Object src, Object target) {

        }
    }
}

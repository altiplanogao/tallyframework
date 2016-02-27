package com.taoswork.tallybook.testmaterial.mongo.domain.zoo.copier;

import com.taoswork.tallybook.datadomain.base.entity.valuecopier.BaseEntityCopier;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class ZooKeeperImplCopier extends BaseEntityCopier<ZooKeeperImpl> {
    private static final Collection<String> sHandledFields;

    static {
        Set<String> localHandledFields = new HashSet<String>();
        localHandledFields.add("id");
        localHandledFields.add("name");
        localHandledFields.add("email");
        localHandledFields.add("mobile");
        localHandledFields.add("uuid");

        sHandledFields = Collections.unmodifiableCollection(localHandledFields);
    }

    @Override
    protected void doCopy(ZooKeeperImpl src, ZooKeeperImpl target) {
        target.setId(src.getId());
        target.setName(src.getName());
        target.setEmail(src.getEmail());
        target.setMobile(src.getMobile());
        target.setUuid(src.getUuid());
    }

    @Override
    public boolean allHandled() {
        return false;
    }

    @Override
    public Collection<String> handledFields() {
        return sHandledFields;
    }
}

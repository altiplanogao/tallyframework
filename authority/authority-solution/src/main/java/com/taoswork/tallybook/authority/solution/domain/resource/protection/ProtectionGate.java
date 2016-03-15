package com.taoswork.tallybook.authority.solution.domain.resource.protection;

import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.solution.domain.resource.DProtectionMode;
import com.taoswork.tallybook.authority.solution.domain.resource.Protection;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseEntityGate;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ProtectionGate extends BaseEntityGate<Protection> {
    @Override
    protected void doStore(Protection entity, Protection oldEntity) {
        if (entity.getProtectionMode() == null){
            entity.setProtectionMode(DProtectionMode.FitAll);
        }
    }

    @Override
    protected void doFetch(Protection entity) {
        if (entity != null){
            if(entity.getProtectionMode() == null){
                entity.setProtectionMode(DProtectionMode.FitAll);
            }
        }
    }
}

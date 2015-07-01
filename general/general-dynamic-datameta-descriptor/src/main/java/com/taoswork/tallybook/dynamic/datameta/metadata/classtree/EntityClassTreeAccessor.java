package com.taoswork.tallybook.dynamic.datameta.metadata.classtree;

import com.taoswork.tallybook.general.solution.autotree.AutoTreeAccessor;

/**
 * Created by Gao Yuan on 2015/5/23.
 */
public class EntityClassTreeAccessor extends AutoTreeAccessor<EntityClass> {
    public EntityClassTreeAccessor() {
        super(new EntityClassGenealogyHelper());
    }

    public EntityClassTree add(EntityClassTree existingNode, Class<?> newNodeData) {
        return (EntityClassTree)super.add(existingNode, new EntityClass(newNodeData));
    }
}

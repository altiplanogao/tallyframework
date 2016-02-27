package com.taoswork.tallybook.descriptor.metadata.classtree;

import com.taoswork.tallybook.general.solution.autotree.AutoTreeAccessor;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeGenealogy;

/**
 * Created by Gao Yuan on 2015/5/23.
 */
public class EntityClassTreeAccessor extends AutoTreeAccessor<EntityClass, EntityClassTree> {
    public EntityClassTreeAccessor() {
        this(new EntityClassGenealogy());
    }

    public EntityClassTreeAccessor(AutoTreeGenealogy<EntityClass> genealogy) {
        super(genealogy);
    }

    public EntityClassTree add(EntityClassTree existingNode, Class<?> newNodeData) {
        return (EntityClassTree) super.add(existingNode, new EntityClass(newNodeData));
    }

    @Override
    public EntityClassTree createNode(EntityClass entityClass) {
        return new EntityClassTree(entityClass);
    }
}

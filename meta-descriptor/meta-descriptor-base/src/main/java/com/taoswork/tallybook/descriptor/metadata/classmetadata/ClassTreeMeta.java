package com.taoswork.tallybook.descriptor.metadata.classmetadata;

import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/6/16.
 */
public class ClassTreeMeta extends MutableClassMeta implements Serializable {
    private final EntityClassTree entityClassTree;

    public ClassTreeMeta(EntityClassTree entityClassTree) {
        super(entityClassTree.getData().clz);
        this.entityClassTree = entityClassTree;
    }

    public EntityClassTree getEntityClassTree() {
        return entityClassTree;
    }

    @Override
    public boolean containsHierarchy() {
        return true;
    }
}

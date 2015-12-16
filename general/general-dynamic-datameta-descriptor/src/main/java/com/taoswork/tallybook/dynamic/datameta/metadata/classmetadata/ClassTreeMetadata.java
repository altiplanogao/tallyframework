package com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/6/16.
 */
public class ClassTreeMetadata extends MutableClassMetadata implements Serializable {
    private final EntityClassTree entityClassTree;

    public ClassTreeMetadata(EntityClassTree entityClassTree) {
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

package com.taoswork.tallybook.dynamic.datameta.metadata;

import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/6/16.
 */
public class ClassTreeMetadata extends ClassMetadata implements Serializable {
    private EntityClassTree entityClassTree;

    public EntityClassTree getEntityClassTree() {
        return entityClassTree;
    }

    public ClassTreeMetadata setEntityClassTree(EntityClassTree entityClassTree) {
        this.entityClassTree = entityClassTree;
        super.setEntityClz(entityClassTree.getData().clz);
        return this;
    }
}

package com.taoswork.tallybook.descriptor.metadata.classtree;

import com.taoswork.tallybook.general.solution.autotree.AutoTree;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class EntityClassTree extends AutoTree<EntityClass> {
    public EntityClassTree(Class<?> clz) {
        this(new EntityClass(clz));
    }

    public EntityClassTree(EntityClass data) {
        super(data);
    }

    @Override
    public String toString() {
        return this.data.toString() + " kids:" + this.kids.size();
    }
}

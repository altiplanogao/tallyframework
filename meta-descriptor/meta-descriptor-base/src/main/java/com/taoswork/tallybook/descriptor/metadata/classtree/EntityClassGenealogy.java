package com.taoswork.tallybook.descriptor.metadata.classtree;

import com.taoswork.tallybook.general.solution.autotree.AutoTreeGenealogy;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.ThreadSafe;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
@ThreadSafe
public class EntityClassGenealogy extends AutoTreeGenealogy<EntityClass> {

    @Override
    public EntityClass calcDirectSuper(EntityClass a, EntityClass referenceSuper) {
        Class<?> clzA = a.clz;
        Class<?> clzRefAncestor = referenceSuper.clz;
        if (!ClassUtility.isAncestorOf(clzRefAncestor, clzA)) {
            return null;
        }

        boolean isClass = !clzA.isInterface();
        if (isClass) {
            Class<?> clzASup = clzA.getSuperclass();
            if (clzRefAncestor.equals(clzASup) || ClassUtility.isAncestorOf(clzRefAncestor, clzASup)) {
                return new EntityClass(clzASup);
            }
        }

        Class<?>[] interfaces = clzA.getInterfaces();
        for (Class<?> inf : interfaces) {
            if (inf.equals(clzRefAncestor) || ClassUtility.isAncestorOf(clzRefAncestor, inf)) {
                return new EntityClass(inf);
            }
        }
        return null;
    }

    @Override
    public EntityClass calcDirectSuperRegardBranch(EntityClass a, EntityClass referenceBranch) {
        return null;
    }

    @Override
    public boolean isSuperOf(EntityClass ancestor, EntityClass descendant) {
        Class<?> clzA = ancestor.clz;
        Class<?> clzB = descendant.clz;
        return ClassUtility.isAncestorOf(clzA, clzB);
    }

    @Override
    public boolean checkEqual(EntityClass a, EntityClass b) {
        return a.equals(b);
    }
}

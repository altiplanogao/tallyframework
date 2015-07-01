package com.taoswork.tallybook.dynamic.datameta.metadata.classtree;

import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeGenealogyHelper;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class EntityClassGenealogyHelper extends AutoTreeGenealogyHelper<EntityClass> {
    @Override
    public AutoTree<EntityClass> createNode(EntityClass entityClass) {
        return new EntityClassTree(entityClass);
    }

    @Override
    public EntityClass calcDataParent(EntityClass a, EntityClass referenceAncestor) {
        Class<?> clzA = a.clz;
        Class<?> clzRefAncestor = referenceAncestor.clz;
        if(!isAncestorOf(clzRefAncestor, clzA)){
            return null;
        }
        boolean notInterface = !clzA.isInterface();

        Class<?> clzASup = clzA.getSuperclass();
        if((notInterface) && (clzRefAncestor.equals(clzASup) || isAncestorOf(clzRefAncestor, clzASup))){
            return new EntityClass(clzASup);
        } else {
            Class<?> [] interfaces = clzA.getInterfaces();
            for (Class<?> inf : interfaces){
                if(inf.equals(clzRefAncestor) || isAncestorOf(clzRefAncestor, inf)){
                    return new EntityClass(inf);
                }
            }
        }
        return null;
    }

    @Override
    public EntityClass calcDataParentRegardBranchNode(EntityClass a, EntityClass referenceBranchNode) {
        return null;
    }

    public boolean isAncestorOf(Class<?> ancestor, Class<?> descendant) {
        if(ancestor.equals(descendant)){
            return false;
        }
        return ancestor.isAssignableFrom(descendant);
    }

    @Override
    public boolean isAncestorOf(EntityClass ancestor, EntityClass descendant) {
        Class<?> clzA = ancestor.clz;
        Class<?> clzB = descendant.clz;
        return isAncestorOf(clzA, clzB);
    }

    @Override
    public boolean checkEqual(EntityClass a, EntityClass b) {
        return a.equals(b);
    }
}

package com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.EntityMetadataHelper;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.helper.impl.EntityMetadataHelper4Hibernate;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import org.hibernate.ejb.HibernateEntityManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/28.
 */
public abstract class DynamicEntityMetadataAccessImplBase implements DynamicEntityMetadataAccess {

    @Resource(name = EntityDescriptionService.SERVICE_NAME)
    private EntityDescriptionService entityDescriptionService;

    private EntityMetadataHelper entityMetadataHelper;

    public abstract EntityManager getEntityManager();

    @PostConstruct
    public void init(){
        EntityManager entityManager = getEntityManager();
        if(entityManager instanceof HibernateEntityManager) {
            entityMetadataHelper = new EntityMetadataHelper4Hibernate();
            entityMetadataHelper.setEntityManager(entityManager);
        }
    }

    @Override
    public EntityClassTree getEntityClassTree(Class<?> entityClz){
        Class<?>[] polymorphicClasses = entityMetadataHelper.getAllPolymorphicEntitiesFromCeiling(entityClz);
        EntityClassTreeAccessor treeAccessor = new EntityClassTreeAccessor();
        treeAccessor.setAllowParent(false).setAllowChild(true).setAllowBranch(false);
        EntityClassTree root = new EntityClassTree(entityClz);
        for(Class<?> polyClz : polymorphicClasses){
            treeAccessor.add(root, new EntityClass(polyClz));
        }
        return root;
    }

    @Override
    public <T> Class<T>  getRootPersistiveEntityClass(Class<T> entityClz){
        final DataHolder<Class<T>> result = new DataHolder<Class<T>>();
        EntityClassTree root = getEntityClassTree(entityClz);
        root.traverse(true, new ICallback2<Void, EntityClass, AutoTree.TraverseControl, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                if (parameter.isPolymorphism()){
                    result.put((Class<T>)parameter.clz);
                    control.shouldBreak = true;
                }
                return null;
            }
        }, false);
        return result.data;
    }

    @Override
    public Map<String, EntityClassTree> getAllEntityClassTree() {
        Class<?>[] entityClasses = entityMetadataHelper.getAllEntityClasses();

        EntityClassTreeAccessor entityClassTreeAccessor = new EntityClassTreeAccessor();
        entityClassTreeAccessor.setAllowBranch(false).setAllowParent(false);
        EntityClassTree root = new EntityClassTree(Serializable.class);
        for (Class<?> entityClz : entityClasses){
            entityClassTreeAccessor.add(root, entityClz);
        }

        Map<String, EntityClassTree> entityClassTrees = new HashMap<String, EntityClassTree>();
        for(Object entityClassTreeObj : root.getReadonlyChildren()){
            EntityClassTree entityClassTree = (EntityClassTree)entityClassTreeObj;
            entityClassTrees.put(entityClassTree.getData().clz.getName(), entityClassTree);
        }
        return entityClassTrees;
    }

    @Override
    public ClassEdo getMergedClassEdo(Class<?> entityClz) {
        EntityClassTree entityClassTree = getEntityClassTree(entityClz);
        final ClassEdo root = entityDescriptionService.createClassEdo(entityClassTree.getData().clz.getName());
        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                ClassEdo node =entityDescriptionService.createClassEdo(parameter.clz.getName());
                root.merge(node);
                return null;
            }
        }, true);

        return root;
    }
}

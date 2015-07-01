package com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper.EntityMetadataHelper;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper.impl.EntityMetadataHelper4JPA;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityMetadataAccess.class);

    @Resource(name = MetadataService.SERVICE_NAME)
    private MetadataService metadataService;

    private EntityMetadataHelper entityMetadataHelper;

    public abstract EntityManager getEntityManager();

    @PostConstruct
    public void init(){
        EntityManager entityManager = getEntityManager();
        entityMetadataHelper = new EntityMetadataHelper4JPA();
        entityMetadataHelper.setEntityManager(entityManager);
//        if(entityManager instanceof HibernateEntityManager) {
//            entityMetadataHelper = new EntityMetadataHelper4Hibernate();
//            entityMetadataHelper.setEntityManager(entityManager);
//        }
        if(entityMetadataHelper == null){
            LOGGER.error("entityMetadataHelper Not initialized !!!");
        }
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
    public <T> Class<T>  getRootPersistiveEntityClass(Class<T> entityClz){
        final DataHolder<Class<T>> result = new DataHolder<Class<T>>();
        EntityClassTree root = getEntityClassTree(entityClz);
        root.traverse(true, new ICallback2<Void, EntityClass, AutoTree.TraverseControl, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                if (parameter.isPolymorphism()) {
                    result.put((Class<T>) parameter.clz);
                    control.shouldBreak = true;
                }
                return null;
            }
        }, false);
        return result.data;
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
    public ClassTreeMetadata getClassTreeMetadata(Class<?> entityClz) {
        EntityClassTree entityClassTree = getEntityClassTree(entityClz);
        return metadataService.getClassTreeMetadata(entityClassTree);
    }
}

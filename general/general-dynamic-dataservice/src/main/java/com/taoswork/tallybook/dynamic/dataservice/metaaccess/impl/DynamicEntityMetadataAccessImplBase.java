package com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassGenealogy;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper.AEntityMetadataRawAccess;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper.impl.EntityMetadataRawAccessJPA;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;

/**
 * Assume we have classes as following:
 * interface IPerson{}                  [entity interface]
 * class Person implements IPerson{}    [entity class],[entity root class]
 * class Male extends Person{}          [entity class]
 * class Female extends Person{}        [entity class]
 */
public abstract class DynamicEntityMetadataAccessImplBase implements DynamicEntityMetadataAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityMetadataAccess.class);

    @Resource(name = MetadataService.SERVICE_NAME)
    private MetadataService metadataService;

    @Resource(name = MetaInfoService.SERVICE_NAME)
    private MetaInfoService metaInfoService;

    private AEntityMetadataRawAccess entityMetaRawAccess;

    private EntityClassGenealogy entityClassGenealogy;
    private EntityClassTree entityClassTreeFromSerializable;

    private List<Class> entityInterfaces = null;
    private Map<Class, Class> type2RootInstanceableMap = null;
    private Map<Class, EntityClassTree> type2ClassTreeMap = null;
    private Map<ClassScope, ClassMetadata> classMetadataMap = null;
    private Map<ClassScopeForInfo, IEntityInfo> entityInfoMap = null;

    public abstract EntityManager getEntityManager();

    @PostConstruct
    public void init() {
        EntityManager entityManager = getEntityManager();
        entityMetaRawAccess = new EntityMetadataRawAccessJPA();
        entityMetaRawAccess.setEntityManager(entityManager);
        if (entityMetaRawAccess == null) {
            LOGGER.error("entityMetaRawAccess Not initialized !!!");
            throw new RuntimeException("entityMetaRawAccess Not initialized !!! DynamicEntityMetadataAccessImplBase cannot continue.");
        } else {
            entityClassGenealogy = new EntityClassGenealogy();
            Class<?>[] entityClasses = entityMetaRawAccess.getAllEntityClasses();

            EntityClassTreeAccessor entityClassTreeAccessor = new EntityClassTreeAccessor(entityClassGenealogy);
            entityClassTreeAccessor.setAllowBranch(false).setAllowParent(false);
            EntityClassTree root = new EntityClassTree(Serializable.class);
            for (Class<?> entityClz : entityClasses) {
                entityClassTreeAccessor.add(root, entityClz);
            }

            List<Class> localEntityInterfaces = new ArrayList<Class>();
            for (Object entityClassTreeObj : root.getReadonlyChildren()) {
                EntityClassTree entityClassTree = (EntityClassTree) entityClassTreeObj;
                localEntityInterfaces.add(entityClassTree.getData().clz);
            }
            entityInterfaces = localEntityInterfaces;
            entityClassTreeFromSerializable = root;
            entityClassTreeAccessor.getGenealogy();
        }

        type2ClassTreeMap = new LRUMap();
        type2RootInstanceableMap = new LRUMap();
        classMetadataMap = new LRUMap();
        entityInfoMap = new LRUMap();
    }

    /**
     * Get the root persistive entity class,
     *
     * @param entityType:
     * @param <T>
     * @return Person
     */
    private <T> Class<T> calcRootInstanceableEntityClass(Class<T> entityType) {
        final DataHolder<Class<T>> result = new DataHolder<Class<T>>();
        EntityClassTree subTree = getEntityClassTree(entityType);
        subTree.traverse(true, new ICallback2<Void, EntityClass, AutoTree.TraverseControl, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                if (parameter.isInstanceable()) {
                    result.put((Class<T>) parameter.clz);
                    control.shouldBreak = true;
                }
                return null;
            }
        }, false);
        return result.data;
    }

    private EntityClassTree calcEntityClassTree(Class<?> entityType) {
        EntityClassTree subTree = entityClassTreeFromSerializable.findChild(new EntityClass(entityType), this.entityClassGenealogy);
        return subTree;
    }

    private IEntityInfo calcEntityInfo(ClassTreeMetadata classTreeMetadata, EntityInfoType infoType) {
        return metaInfoService.generateEntityInfo(classTreeMetadata, infoType);
    }

    @Override
    public Collection<Class> getAllEntityInterfaces() {
        return Collections.unmodifiableCollection(entityInterfaces);
    }

    @Override
    public <T> Class<T> getRootInstanceableEntityClass(Class<T> entityType) {
        Class<T> root = type2RootInstanceableMap.getOrDefault(entityType, null);
        if (null == root) {
            root = calcRootInstanceableEntityClass(entityType);
            type2RootInstanceableMap.put(entityType, root);
        }
        return root;
    }

    @Override
    public EntityClassTree getEntityClassTree(Class<?> entityType) {
        EntityClassTree classTree = type2ClassTreeMap.getOrDefault(entityType, null);
        if (classTree == null) {
            classTree = calcEntityClassTree(entityType);
            type2ClassTreeMap.put(entityType, classTree);
        }
        return classTree;

    }

    @Override
    public ClassTreeMetadata getClassTreeMetadata(Class<?> entityType) {
        ClassScope classScope = new ClassScope(entityType, true, true);
        return getClassTreeMetadata(entityType, classScope);
    }

    private ClassTreeMetadata getClassTreeMetadata(Class<?> entityType, ClassScope classScope) {
        ClassMetadata classMetadata = classMetadataMap.getOrDefault(classScope, null);
        if (null == classMetadata) {
            EntityClassTree entityClassTree = getEntityClassTree(entityType);
            classMetadata = metadataService.generateMetadata(entityClassTree, classScope.isWithSuper());
            classMetadataMap.put(classScope, classMetadata);
        }
        if (classMetadata instanceof ClassTreeMetadata) {
            return (ClassTreeMetadata) classMetadata;
        }
        return null;
    }

    @Override
    public IEntityInfo getEntityInfo(Class<?> entityType, EntityInfoType infoType) {
        ClassScope classScope = new ClassScope(entityType, true, true);
        ClassScopeForInfo classScopeForInfo = new ClassScopeForInfo(classScope, infoType);

        IEntityInfo entityInfo = entityInfoMap.getOrDefault(classScopeForInfo, null);
        if (entityInfo == null) {
            ClassTreeMetadata classTreeMetadata = getClassTreeMetadata(entityType, classScope);
            entityInfo = calcEntityInfo(classTreeMetadata, infoType);
            entityInfoMap.put(classScopeForInfo, entityInfo);
        }
        return entityInfo;
    }

    //    private void codeSni(){
//
//        { //EntityClassTree getEntityClassTree(Class<?> entityClz)
//            Class<?>[] polymorphicClasses = entityMetaRawAccess.getAllInstanceableEntitiesFromCeiling(entityClz);
//            EntityClassTreeAccessor treeAccessor = new EntityClassTreeAccessor();
//            treeAccessor.setAllowParent(false).setAllowChild(true).setAllowBranch(false);
//            EntityClassTree root = new EntityClassTree(entityClz);
//            for (Class<?> polyClz : polymorphicClasses) {
//                treeAccessor.add(root, new EntityClass(polyClz));
//            }
//            return root;
//        }
//    }
}

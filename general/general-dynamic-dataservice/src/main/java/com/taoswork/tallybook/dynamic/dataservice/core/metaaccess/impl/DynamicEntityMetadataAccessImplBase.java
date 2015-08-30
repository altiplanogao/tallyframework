package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.MetaInfoService;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassGenealogy;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.AEntityMetadataRawAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.impl.EntityMetadataRawAccessJPA;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.EffectivelyImmutable;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Assume we have classes as following:
 * interface IPerson{}                  [entity interface]
 * class Person implements IPerson{}    [entity class],[entity root class]
 * class Male extends Person{}          [entity class]
 * class Female extends Person{}        [entity class]
 */
public abstract class DynamicEntityMetadataAccessImplBase implements DynamicEntityMetadataAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityMetadataAccess.class);
    private static class ClassScopeWithLocale extends ClassScopeExtension<Locale>{
        public ClassScopeWithLocale(ClassScope classScope, Locale note) {
            super(classScope, note);
        }

        public ClassScopeWithLocale(String clazzName, boolean withSuper, boolean withHierarchy, Locale note) {
            super(clazzName, withSuper, withHierarchy, note);
        }

        public ClassScopeWithLocale(Class clazz, boolean withSuper, boolean withHierarchy, Locale note) {
            super(clazz, withSuper, withHierarchy, note);
        }
    }
    private static class ClassScopeWithLocaleAndType extends ClassScopeExtension<_FriendlyEntityInfoType> {
        public ClassScopeWithLocaleAndType(ClassScope classScope, _FriendlyEntityInfoType note) {
            super(classScope, note);
        }

        public ClassScopeWithLocaleAndType(String clazzName, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
            super(clazzName, withSuper, withHierarchy, note);
        }

        public ClassScopeWithLocaleAndType(Class clazz, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
            super(clazz, withSuper, withHierarchy, note);
        }
    }

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = MetadataService.SERVICE_NAME)
    private MetadataService metadataService;

    @Resource(name = MetaInfoService.SERVICE_NAME)
    private MetaInfoService metaInfoService;

    @Resource(name = FriendlyMetaInfoService.SERVICE_NAME)
    private FriendlyMetaInfoService friendlyMetaInfoService;

    @EffectivelyImmutable
    private AEntityMetadataRawAccess entityMetaRawAccess;

    private final EntityClassGenealogy entityClassGenealogy = new EntityClassGenealogy();

    @EffectivelyImmutable
    private Collection<Class> entityInterfaces = null;

    @GuardedBy("self")
    private Map<Class, Class> type2RootInstanceableMap = null;

    @GuardedBy(value = "self", lockOrder = 5)
    private Map<Class, EntityClassTree> type2ClassTreeMap = null;
    @GuardedBy(value = "self", lockOrder = 4)
    private Map<ClassScope, ClassMetadata> classMetadataMap = null;

    @GuardedBy(value = "self", lockOrder = 3)
    private Map<ClassScope, EntityInfo> entityInfoMap = null;
    @GuardedBy(value = "self", lockOrder = 2)
    private Map<ClassScopeWithLocale, EntityInfo> entityLocaleInfoMap = null;
    @GuardedBy(value = "self", lockOrder = 1)
    private Map<ClassScopeWithLocaleAndType, IEntityInfo> entityLocaleAndTypeInfoMap = null;

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
            Class<?>[] entityClasses = entityMetaRawAccess.getAllEntityClasses();
            entityInterfaces = ClassUtility.getAllImplementedInterfaces(Serializable.class, entityClasses);
        }

        type2ClassTreeMap = new LRUMap();
        type2RootInstanceableMap = new LRUMap();

        classMetadataMap = new LRUMap();

        entityInfoMap = new LRUMap();
        entityLocaleInfoMap = new LRUMap();
        entityLocaleAndTypeInfoMap = new LRUMap();
    }

    //Used for easy debug
    private void clearCacheMaps(){
        type2ClassTreeMap.clear();
        type2RootInstanceableMap.clear();
        classMetadataMap.clear();

        entityInfoMap.clear();
        entityLocaleInfoMap.clear();
        entityLocaleAndTypeInfoMap.clear();
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

    private EntityClassTree calcEntityClassTreeFromCeiling(Class<?> ceilingType){
        Class<?>[] entityClasses = entityMetaRawAccess.getAllEntityClasses();

        EntityClassTreeAccessor entityClassTreeAccessor = new EntityClassTreeAccessor(entityClassGenealogy);
        entityClassTreeAccessor.setAllowBranch(false).setAllowParent(false);
        EntityClassTree root = new EntityClassTree(ceilingType);
        for (Class<?> entityClz : entityClasses) {
            entityClassTreeAccessor.add(root, entityClz);
        }
        return root;
    }

    private EntityInfo calcEntityInfo(ClassTreeMetadata classTreeMetadata) {
        return metaInfoService.generateEntityMainInfo(classTreeMetadata);
    }

    private EntityInfo calcFriendlyEntityInfo(EntityInfo rawEntityInfo, Locale locale){
        return friendlyMetaInfoService.makeFriendly(rawEntityInfo, locale);
    }

    @Override
    public Collection<Class> getAllEntityInterfaces() {
        return Collections.unmodifiableCollection(entityInterfaces);
    }

    @Override
    public <T> Class<T> getRootInstanceableEntityClass(Class<T> entityType) {
        synchronized (type2RootInstanceableMap) {
            Class<T> root = type2RootInstanceableMap.getOrDefault(entityType, null);
            if (null == root) {
                root = calcRootInstanceableEntityClass(entityType);
                type2RootInstanceableMap.put(entityType, root);
            }
            return root;
        }
    }

    @Override
    public EntityClassTree getEntityClassTree(Class<?> entityType) {
        synchronized (type2ClassTreeMap){
            EntityClassTree classTree = type2ClassTreeMap.getOrDefault(entityType, null);
            if (classTree == null) {
                classTree = calcEntityClassTreeFromCeiling(entityType);
                type2ClassTreeMap.put(entityType, classTree);
            }
            return classTree;
        }
    }

    @Override
    public ClassTreeMetadata getClassTreeMetadata(Class<?> entityType) {
        ClassScope classScope = new ClassScope(entityType, true, true);
        return getClassTreeMetadata(entityType, classScope);
    }

    private ClassTreeMetadata getClassTreeMetadata(Class<?> entityType, ClassScope classScope) {
        ClassMetadata classMetadata = null;
        synchronized (classMetadataMap){
            classMetadata = classMetadataMap.getOrDefault(classScope, null);
            if (null == classMetadata) {
                EntityClassTree entityClassTree = getEntityClassTree(entityType);
                classMetadata = metadataService.generateMetadata(entityClassTree);
                classMetadataMap.put(classScope, classMetadata);
            }
        }
        if (classMetadata instanceof ClassTreeMetadata) {
            return (ClassTreeMetadata) classMetadata;
        }
        return null;
    }

    @Override
    public IEntityInfo getEntityInfo(Class<?> entityType, Locale locale, EntityInfoType infoType) {
        ClassScope classScope = new ClassScope(entityType, true, true);
        ClassScopeWithLocaleAndType scope = new ClassScopeWithLocaleAndType(classScope, new _FriendlyEntityInfoType(infoType, locale));
        synchronized (entityLocaleAndTypeInfoMap) {
            IEntityInfo entityInfo = entityLocaleAndTypeInfoMap.getOrDefault(scope, null);
            if (entityInfo == null) {
                entityInfo = calcEntityInfo(entityType, locale, infoType);
                entityLocaleAndTypeInfoMap.put(scope, entityInfo);
            }
            return entityInfo;
        }
    }

    private IEntityInfo calcEntityInfo(Class<?> entityType, Locale locale, EntityInfoType infoType) {
        ClassScope classScope = new ClassScope(entityType, true, true);
        ClassScopeWithLocale scope = new ClassScopeWithLocale(classScope, locale);
        EntityInfo entityLocInfo = getEntityInfo(entityType, classScope, locale);
        return metaInfoService.convert(entityLocInfo, infoType);
    }

    private EntityInfo getEntityInfo(Class<?> entityType, ClassScope classScope, Locale locale){
        ClassScopeWithLocale scope = new ClassScopeWithLocale(classScope, locale);
        synchronized (entityLocaleInfoMap) {
            EntityInfo entityInfo = entityLocaleInfoMap.getOrDefault(scope, null);
            if (entityInfo == null) {
                entityInfo = calcEntityInfo(entityType, classScope, locale);
                entityLocaleInfoMap.put(scope, entityInfo);
            }
            return entityInfo;
        }
    }

    private EntityInfo calcEntityInfo(Class<?> entityType, ClassScope classScope, Locale locale){
        EntityInfo entityInfo = getEntityInfo(entityType, classScope);
        return calcFriendlyEntityInfo(entityInfo, locale);
    }

    private EntityInfo getEntityInfo(Class<?> entityType, ClassScope classScope) {
        synchronized (entityInfoMap) {
            EntityInfo entityInfo = entityInfoMap.getOrDefault(classScope, null);
            if (entityInfo == null) {
                entityInfo = calcEntityInfo(entityType, classScope);
                entityInfoMap.put(classScope, entityInfo);
            }
            return entityInfo;
        }
    }

    private EntityInfo calcEntityInfo(Class<?> entityType, ClassScope classScope) {
        ClassTreeMetadata classTreeMetadata = getClassTreeMetadata(entityType, classScope);
        EntityInfo entityInfo = calcEntityInfo(classTreeMetadata);
        return entityInfo;
    }
}

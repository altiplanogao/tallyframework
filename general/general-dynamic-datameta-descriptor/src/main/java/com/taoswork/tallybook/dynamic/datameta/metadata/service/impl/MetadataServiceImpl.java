package com.taoswork.tallybook.dynamic.datameta.metadata.service.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.FieldProcessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import org.apache.commons.collections.map.LRUMap;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MetadataServiceImpl implements MetadataService {
    protected final ClassProcessor classProcessor;
    protected final FieldProcessor fieldProcessor;

    private Map<String, ClassMetadata> classMetadataCache = new LRUMap();

    public MetadataServiceImpl(){
        fieldProcessor = new FieldProcessor();
        classProcessor = new ClassProcessor(fieldProcessor);
    }

    @Override
    public ClassTreeMetadata getClassTreeMetadata(final EntityClassTree entityClassTree) {
        final ClassTreeMetadata classTreeMetadata = new ClassTreeMetadata();
        classTreeMetadata.setEntityClassTree(entityClassTree);
        generateClassMetadata(entityClassTree.getData().clz, classTreeMetadata);

        entityClassTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                Class clz = parameter.clz;
                ClassMetadata classMetadata = getClassMetadata(clz);
                if(clz.equals(entityClassTree.getData().clz)){
                    classTreeMetadata.copyFrom(classMetadata);
                }
                classTreeMetadata.getRWTabMetadataMap().putAll(classMetadata.getReadonlyTabMetadataMap());
                classTreeMetadata.getRWGroupMetadataMap().putAll(classMetadata.getReadonlyGroupMetadataMap());
                classTreeMetadata.getRWFieldMetadataMap().putAll(classMetadata.getReadonlyFieldMetadataMap());
                return null;
            }
        }, false);

        return classTreeMetadata;
    }

    @Override
    public ClassMetadata getClassMetadata(Class clz){
        return this.getClassMetadata(clz.getName());
    }

    @Override
    public ClassMetadata getClassMetadata(String clzName){
        ClassMetadata classMetadata = classMetadataCache.getOrDefault(clzName, null);
        if(classMetadata == null){
            try{
                classMetadata = generateClassMetadata(Class.forName(clzName));
                classMetadataCache.put(clzName, classMetadata);
            } catch (ClassNotFoundException exp){
                throw new RuntimeException(exp);
            }
        }
        return classMetadata;
    }

//    @Override
//    public FieldMetadata getFieldMetadata(Field field){
//        FieldMetadata fieldMetadata = new FieldMetadata(field);
//        fieldProcessor.process(field, fieldMetadata);
//        return fieldMetadata;
//    }

    private ClassMetadata generateClassMetadata(Class<?> clz){
        ClassMetadata classMetadata = new ClassMetadata();
        generateClassMetadata(clz, classMetadata);
        return classMetadata;
    }

    private void generateClassMetadata(Class<?> clz, ClassMetadata classMetadata){
        classProcessor.process(clz, classMetadata);
    }
}
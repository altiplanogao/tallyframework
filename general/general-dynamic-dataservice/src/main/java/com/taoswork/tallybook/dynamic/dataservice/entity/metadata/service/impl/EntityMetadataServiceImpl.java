package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ClassProcessor;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.FieldProcessor;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import org.apache.commons.collections.map.LRUMap;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class EntityMetadataServiceImpl implements EntityMetadataService {
    protected final ClassProcessor classProcessor;
    protected final FieldProcessor fieldProcessor;
    private Map<String, ClassMetadata> classMetadataCache = new LRUMap();

    public EntityMetadataServiceImpl(){
        fieldProcessor = new FieldProcessor();
        classProcessor = new ClassProcessor(fieldProcessor);
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

    @Override
    public FieldMetadata getFieldMetadata(Field field){
        FieldMetadata fieldMetadata = new FieldMetadata(field);
        fieldProcessor.process(field, fieldMetadata);
        return fieldMetadata;
    }

    private ClassMetadata generateClassMetadata(Class<?> clz){
        ClassMetadata classMetadata = new ClassMetadata();
        classProcessor.process(clz, classMetadata);
        return classMetadata;
    }
}

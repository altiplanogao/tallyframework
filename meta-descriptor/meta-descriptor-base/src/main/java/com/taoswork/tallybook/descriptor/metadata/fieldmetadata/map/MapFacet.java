package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map;

import com.taoswork.tallybook.datadomain.base.entity.MapField;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;
import com.taoswork.tallybook.general.solution.reflect.GenericTypeUtility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class MapFacet implements IFacet {
    protected final Class mapType;
    protected final Class mapImplType;

    protected Class keyType;
    protected Class valueTargetType;
    //declared value class is not important;

    public MapFacet(Class mapType, Class mapImplType,
                    Class keyType,
                    Class valueTargetType) {
        this.mapType = mapType;
        this.mapImplType = mapImplType;
        this.keyType = keyType;
        this.valueTargetType = valueTargetType;
    }

    public MapFacet(MapFacet other) {
        this.mapType = other.mapType;
        this.mapImplType = other.mapImplType;
        this.keyType = other.keyType;
        this.valueTargetType = other.valueTargetType;
    }

    public MapFacet(Field field, boolean useAnnotation, Class explicitValType) {
        if (useAnnotation) {
            MapField mf = field.getDeclaredAnnotation(MapField.class);
            if (mf == null) {
                MetadataException.throwFieldConfigurationException(field, "@MapField not defined");
            }

            Class mapClz = field.getType();
            Class mapImplClz = mf.mapImplementationClass();
            if (mapImplClz.equals(void.class)) {
                mapImplClz = workOutMapImplementType(mapClz);
            }

            Class keyClz = GenericTypeUtility.getGenericArgument(field, 0);
            Class valClz = GenericTypeUtility.getGenericArgument(field, 1);

            Class targetClz = mf.targetClass();
            if (targetClz.equals(void.class)) {
                targetClz = valClz;
            }

            this.mapType = mapClz;
            this.mapImplType = mapImplClz;
            this.keyType = keyClz;
            this.valueTargetType = targetClz;
        } else {
            this.mapType = field.getType();
            this.mapImplType = workOutMapImplementType(mapType);
            this.keyType = GenericTypeUtility.getGenericArgument(field, 0);
            this.valueTargetType = GenericTypeUtility.getGenericArgument(field, 1);
        }
        if(explicitValType != null && !void.class.equals(explicitValType)){
            this.valueTargetType = explicitValType;
        }
    }

    public Class getMapType() {
        return mapType;
    }

    public Class getMapImplType() {
        return mapImplType;
    }

    public void setValueTargetType(Class valueTargetType) {
        this.valueTargetType = valueTargetType;
    }

    public Class getValueTargetType() {
        return valueTargetType;
    }

    public static Class workOutMapImplementType(Class collectionType) {
        try {
            Constructor constructor = collectionType.getConstructor(new Class[]{});
            return collectionType;
        } catch (NoSuchMethodException e) {
            //Ignore this exception, because it is not an instantiatable object.
        }
        if (Map.class.equals(collectionType)) {
            return HashMap.class;
        } else {
            return HashMap.class;
        }
    }
}

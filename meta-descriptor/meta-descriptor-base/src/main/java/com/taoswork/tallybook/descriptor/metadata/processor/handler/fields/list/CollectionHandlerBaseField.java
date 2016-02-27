package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list;

import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;
import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;
import com.taoswork.tallybook.general.solution.Tristate;
import com.taoswork.tallybook.general.solution.reflect.AnnotationUtility;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public abstract class CollectionHandlerBaseField extends BaseFieldHandler {
    private static Set<Class> primitiveTypes = new HashSet<Class>();
    private static Set<Class> primitiveBases = new HashSet<Class>();
    static {
        primitiveTypes.add(Integer.class);
        primitiveTypes.add(int.class);
        primitiveTypes.add(Long.class);
        primitiveTypes.add(long.class);
        primitiveTypes.add(Double.class);
        primitiveTypes.add(double.class);
        primitiveTypes.add(Float.class);
        primitiveTypes.add(float.class);

        primitiveTypes.add(String.class);

        primitiveBases.add(IFriendlyEnum.class);
    }

    public static Tristate isPrimitiveType(Class clz){
        if(clz == null)
            return Tristate.False;
        if(primitiveTypes.contains(clz)){
            return Tristate.True;
        }
        if(clz.isEnum()){
            return Tristate.True;
        }
        for(Class primitiveSuper : primitiveBases){
            if(primitiveSuper.isAssignableFrom(clz)){
                return Tristate.True;
            }
        }

        boolean annotatedAsEmbedded = AnnotationUtility.isClassAnnotated(clz, "Embeddable") ||
                AnnotationUtility.isClassAnnotated(clz, "Embedded");
        if(annotatedAsEmbedded)
            return Tristate.False;

        return Tristate.Unsure;
    }

}

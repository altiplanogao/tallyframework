package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PaleFieldMeta
        extends BasePrimitiveFieldMeta {

    protected static interface FromStringConvertor<T>{
        T fromString(String v);
    }
    protected static class Convertors{
        static class LongConvertor implements FromStringConvertor<Long>{
            @Override
            public Long fromString(String v) {
                return Long.valueOf(v);
            }
        }
        static class IntConvertor implements FromStringConvertor<Integer>{
            @Override
            public Integer fromString(String v) {
                return Integer.valueOf(v);
            }
        }

    }

    private static final Map<Class, FromStringConvertor> convertors;
    static {
        Map<Class, FromStringConvertor> convertorMap = new HashMap<Class, FromStringConvertor>();
        convertorMap.put(Long.class, new Convertors.LongConvertor());
        convertorMap.put(Integer.class, new Convertors.IntConvertor());

        convertors = Collections.unmodifiableMap(convertorMap);
    }

    public PaleFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.UNKNOWN;
    }

    /**
     * Created by Gao Yuan on 2016/2/19.
     */
    public static class Seed implements IFieldMetaSeed {
        public Seed() {
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new PaleFieldMeta(bfmo);
        }
    }

    @Override
    public Object getValueFromString(String valStr) {
        Class fieldClz = this.getFieldClass();
        FromStringConvertor convertor = convertors.get(fieldClz);
        if(convertor == null)
            return valStr;
        return convertor.fromString(valStr);
    }
}

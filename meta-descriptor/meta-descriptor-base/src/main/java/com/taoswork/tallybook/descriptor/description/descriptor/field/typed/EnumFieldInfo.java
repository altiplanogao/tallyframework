package com.taoswork.tallybook.descriptor.description.descriptor.field.typed;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationEnumClass;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.BasicFieldInfoBase;
import com.taoswork.tallybook.descriptor.metadata.utils.FriendlyNameHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class EnumFieldInfo extends BasicFieldInfoBase {
    private final List<String> options = new ArrayList<String>();
    private final Map<String, String> optionsFriendly = new HashMap<String, String>();
    private String typeName;
    private String typeFriendlyName;

    public EnumFieldInfo(String name, String friendlyName, boolean editable, Class<?> enumClz) {
        super(name, friendlyName, editable);
        if (!enumClz.isEnum()) {
            throw new IllegalArgumentException();
        }

        typeName = enumClz.getSimpleName();
        typeFriendlyName = FriendlyNameHelper.makeFriendlyName4EnumClass(enumClz);

        try {
            Method valuesMethod = enumClz.getMethod("values", new Class[]{});
            PresentationEnumClass presentationEnumClass = enumClz.getAnnotation(PresentationEnumClass.class);
            if (presentationEnumClass != null) {

            }
            boolean isStatic = Modifier.isStatic(valuesMethod.getModifiers());
            Object[] enumVals = (Object[]) (valuesMethod.invoke(null));
            for (Object val : enumVals) {
                String key = val.toString();
                String value = FriendlyNameHelper.makeFriendlyName4EnumValue(enumClz, val);
                optionsFriendly.put(key, value);
                options.add(key);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Collection<String> getOptions() {
        return Collections.unmodifiableCollection(options);
    }

    public String getFriendlyName(String option) {
        if (optionsFriendly != null) {
            return optionsFriendly.get(option);
        }
        return null;
    }

    public void setFriendlyName(String option, String friendlyName) {
        optionsFriendly.put(option, friendlyName);
    }

    public Map<String, String> getOptionsFriendly() {
        return Collections.unmodifiableMap(optionsFriendly);
    }

}

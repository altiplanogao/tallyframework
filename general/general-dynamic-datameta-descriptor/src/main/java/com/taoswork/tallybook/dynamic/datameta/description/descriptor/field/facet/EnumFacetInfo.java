package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.facet;

import com.taoswork.tallybook.dynamic.datameta.metadata.FieldFacetType;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/1.
 */
public class EnumFacetInfo implements IFieldFacet{
    public EnumFacetInfo(Class<?> enumClz){
        if(!enumClz.isEnum()){
            throw new IllegalArgumentException();
        }

        typeName = enumClz.getSimpleName();
        typeFriendlyName = FriendlyNameHelper.makeFriendlyName4EnumClass(enumClz);

        try {
            Method valuesMethod = enumClz.getMethod("values", new Class[]{});
            PresentationEnum presentationEnum = enumClz.getAnnotation(PresentationEnum.class);
            if(presentationEnum != null){

            }
            boolean isStatic = Modifier.isStatic(valuesMethod.getModifiers());
            Object[] enumVals = (Object[])(valuesMethod.invoke(null));
            for (Object val : enumVals){
                String key = val.toString();
                String value = FriendlyNameHelper.makeFriendlyName4EnumValue(enumClz, val);
                friendlyNames.put(key, value);
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

    @Override
    public FieldFacetType getType() {
        return FieldFacetType.Enum;
    }

    private String typeName;
    private String typeFriendlyName;
    private final List<String> options = new ArrayList<String>();
    private final Map<String, String> friendlyNames = new HashMap<String, String>();

    public Collection<String> getOptions(){
        return Collections.unmodifiableCollection(options);
    }

    public String getFriendlyName(String option){
        if(friendlyNames != null){
            return friendlyNames.getOrDefault(option, null);
        }
        return null;
    }

    public void setFriendlyName(String option, String friendlyName){
        friendlyNames.put(option, friendlyName);
    }

    public Map<String, String> getFriendlyNames(){
        return friendlyNames;
    }
}
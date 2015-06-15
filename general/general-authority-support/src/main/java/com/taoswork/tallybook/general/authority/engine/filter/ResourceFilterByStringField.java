package com.taoswork.tallybook.general.authority.engine.filter;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class ResourceFilterByStringField implements IResourceFilter {
    private Class resourceClz;
    private String fieldName;
    private String fieldExpectedValue;
    private boolean valid = true;

    @Override
    public void setResourceTypeName(String resourceTypeName){
        try {
            this.resourceClz = Class.forName(resourceTypeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResourceTypeName() {
        return resourceClz.getName();
    }

    @Override
    public void setFilterParameter(String parameter){
        valid = false;
        if(null == parameter){
            return;
        }
        String [] params = parameter.split(":");
        if(params.length == 2) {
            fieldName = params[0];
            fieldExpectedValue = params[1];
            valid = true;
        }
    }

    @Override
    public boolean isMatch(Object entity){
        if(null == entity){
            return false;
        }
        if(valid){
            Class entityClz = entity.getClass();
            if(resourceClz.isAssignableFrom(entityClz)){
                try {
                    Field field = entityClz.getField(fieldName);
                    field.setAccessible(true);
                    String value = (String)field.get(entity);
                    if(fieldExpectedValue.equals(value)){
                        return true;
                    }
                }catch (NoSuchFieldException exp){
                    return false;
                } catch (IllegalAccessException e) {
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public String unMatchQueryInterrupter() {
        return fieldName + " not " + fieldExpectedValue;
    }
}

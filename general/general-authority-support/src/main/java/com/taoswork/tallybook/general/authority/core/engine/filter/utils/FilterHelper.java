package com.taoswork.tallybook.general.authority.core.engine.filter.utils;

import com.taoswork.tallybook.general.authority.core.engine.filter.IResourceFilter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class FilterHelper {
    public static IResourceFilter createFilter(String resourceTypeName, String filterName, String filterParameter){
        try {
            Class filterClz = Class.forName(filterName);
            if(IResourceFilter.class.isAssignableFrom(filterClz)){
                Constructor filterConstructor = filterClz.getConstructor(new Class[]{});
                IResourceFilter filter = (IResourceFilter)filterConstructor.newInstance(new Object[]{});
                filter.setResourceTypeName(resourceTypeName);
                filter.setFilterParameter(filterParameter);
                return filter;
            }else {
                return null;
            }
        }catch (ClassNotFoundException exp){
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

    }
}

package com.taoswork.tallybook.general.authority.core.engine.filter.utils;

import com.taoswork.tallybook.general.authority.core.engine.filter.IResourceFilter;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class FilterHelper {
    public static IResourceFilter createFilter(String resourceTypeName, String filterName, String filterParameter){
        try {
            Class filterClz = Class.forName(filterName);

            if(IResourceFilter.class.isAssignableFrom(filterClz)){
                IResourceFilter filter = (IResourceFilter)filterClz.newInstance();
                filter.setResourceTypeName(resourceTypeName);
                filter.setFilterParameter(filterParameter);
                return filter;
            }else {
                return null;
            }
        }catch (ClassNotFoundException exp){
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }

    }
}

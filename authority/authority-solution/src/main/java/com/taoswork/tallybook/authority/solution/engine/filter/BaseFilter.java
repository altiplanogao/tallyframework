package com.taoswork.tallybook.authority.solution.engine.filter;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public abstract class BaseFilter implements IFilter {

    private String resource;
 //   private Class resourceClz;

    private String parameter;

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public void setResource(String resource) {
//        try {
//            this.resourceClz = Class.forName(resource);
            this.resource = resource;
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }
//
//    protected Class getResourceClz() {
//        return resourceClz;
//    }

    @Override
    public String getFilterParameter() {
        return parameter;
    }

    @Override
    public void setFilterParameter(String parameter) {
        this.parameter = parameter;
        doAnalyzeFilterParameter(parameter);
    }

    protected abstract void doAnalyzeFilterParameter(String parameter);
}

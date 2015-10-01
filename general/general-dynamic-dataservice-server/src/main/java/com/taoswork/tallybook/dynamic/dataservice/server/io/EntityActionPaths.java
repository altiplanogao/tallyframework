package com.taoswork.tallybook.dynamic.dataservice.server.io;

/**
 * {@link com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames}
 */
public class EntityActionPaths {
    private EntityActionPaths()throws IllegalAccessException{throw new IllegalAccessException("Not instanceable object");}

    public static final String CREATE = "/add";    //view add page: RequestMethod.GET, do add : RequestMethod.POST
    public static final String READ = "";              //RequestMethod.GET
    public static final String UPDATE = "";            //RequestMethod.POST
    public static final String DELETE = "/delete";    //RequestMethod.POST
    public static final String QUERY = "/";                 //RequestMethod.GET

    public static final String INSPECT = "/info";           //RequestMethod.GET
}

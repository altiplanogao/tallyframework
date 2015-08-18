package com.taoswork.tallybook.dynamic.dataservice.server.io;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class EntityActionPaths {
    private EntityActionPaths()throws IllegalAccessException{throw new IllegalAccessException("Not instanceable object");}

    public static final String ADD = "/add";    //view add page: RequestMethod.GET, do add : RequestMethod.POST
    public static final String SEARCH = "/";                 //RequestMethod.GET
    public static final String INSPECT = "/info";           //RequestMethod.GET

    public static final String READ = "";              //RequestMethod.GET
    public static final String UPDATE = "";            //RequestMethod.POST
    public static final String DELETE = "/delete";    //RequestMethod.POST
}

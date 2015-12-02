package com.taoswork.tallybook.dynamic.dataservice.server.io;

/**
 * {@link com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames}
 */
public class EntityActionPaths {
    private EntityActionPaths()throws IllegalAccessException{throw new IllegalAccessException("Not instantiable object");}

    private static final String ENTITY = "{entity}";
    private static final String ENTITY_RECORD = ENTITY + "/{id}";

    public static final String ID_KEY = "id";

    private static final String ENTITY_CREATE = "/add";    //view add page: RequestMethod.GET, do add : RequestMethod.POST
    private static final String ENTITY_READ = "/{id}";              //RequestMethod.GET
    private static final String ENTITY_UPDATE = "/{id}";           //RequestMethod.POST
    private static final String ENTITY_DELETE = "/{id}/delete";    //RequestMethod.POST
    private static final String ENTITY_QUERY = "";                 //RequestMethod.GET
    private static final String ENTITY_INFO = "/info";           //RequestMethod.GET

    public static final String ENTITY_COLLECTION = ENTITY + "/{field}";

    public static String uriTemplateForAdd (String entityName){
        return entityName + ENTITY_CREATE;
    }
    public static String uriTemplateForRead (String entityName){
        return "/" + entityName + ENTITY_READ;
    }
    public static String uriTemplateForUpdate (String entityName){
        return "/" + entityName + ENTITY_UPDATE;
    }
    public static String uriTemplateForDelete (String entityName){
        return "/" + entityName + ENTITY_DELETE;
    }
    public static String uriTemplateForQuery (String entityName){
        return "/" + entityName + ENTITY_QUERY;
    }
    public static String uriTemplateForInfo (String entityName){
        return "/" + entityName + ENTITY_INFO;
    }

}

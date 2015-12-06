package com.taoswork.tallybook.dynamic.dataservice.server.io;

/**
 * {@link com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames}
 */
public class EntityActionPaths {
    private EntityActionPaths() throws IllegalAccessException {
        throw new IllegalAccessException("Not instantiable object");
    }

    public static final String ENTITY_KEY = "entity";
    public static final String ID_KEY = "id";
    public static final String FIELD_KEY = "field";
    public static final String ITEM_KEY = "item";

    private static final String ENTITY = "\\{entity\\}";
    private static final String FIELD = "\\{field\\}";
    private static final String ITEM = "\\{item\\}";

    public final static class EntityUris{
        private static final String L_ENTITY_QUERY = "/{entity}";                   //RequestMethod.GET
        private static final String L_ENTITY_INFO = "/{entity}/info";              //RequestMethod.GET
        private static final String L_ENTITY_CREATE = "/{entity}/add";              //view add page: RequestMethod.GET, do add : RequestMethod.POST
        private static final String L_ENTITY_READ = "/{entity}/{id}";              //RequestMethod.GET
        private static final String L_ENTITY_UPDATE = "/{entity}/{id}";            //RequestMethod.POST
        private static final String L_ENTITY_DELETE = "/{entity}/{id}/delete";    //RequestMethod.POST

        public static String uriTemplateForCreate(String entityName) {
            return L_ENTITY_CREATE.replaceFirst(ENTITY, entityName);
        }

        public static String uriTemplateForRead(String entityName) {
            return L_ENTITY_READ.replaceFirst(ENTITY, entityName);
        }

        public static String uriTemplateForUpdate(String entityName) {
            return L_ENTITY_UPDATE.replaceFirst(ENTITY, entityName);
        }

        public static String uriTemplateForDelete(String entityName) {
            return L_ENTITY_DELETE.replaceFirst(ENTITY, entityName);
        }

        public static String uriTemplateForQuery(String entityName) {
            return L_ENTITY_QUERY.replaceFirst(ENTITY, entityName);
        }

        public static String uriTemplateForInfo(String entityName) {
            return L_ENTITY_INFO.replaceFirst(ENTITY, entityName);
        }
    }

    public static final class EntityFieldUris{
        private static final String L_ENTITY_FIELD_INFO = "/{entity}/{field}/info";  //RequestMethod.GET
        private static final String L_ENTITY_FIELD_CREATE = "/{entity}/{field}/add";     //RequestMethod.GET
        private static final String L_ENTITY_FIELD_SELECT = "/{entity}/{field}/select";  //RequestMethod.GET
        private static final String L_ENTITY_FIELD_TYPEAHEAD = "/{entity}/{field}/typeahead";    //RequestMethod.GET

    }

    public static final class BeanFieldUris{
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_QUERY = "/{entity}/{id}/{field}";
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_CREATE = "/{entity}/{id}/{field}/add";
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_READ = "/{entity}/{id}/{field}/{item}";
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_UPDATE = "/{entity}/{id}/{field}/{item}";
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_DELETE = "/{entity}/{id}/{field}/{item}/delete";
        private static final String L_ENTITY_BEAN_COLLECTION_FIELD_ITEM_REORDER = "/{entity}/{id}/{field}/{item}/reorder";

    }


}

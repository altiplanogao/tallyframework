package com.taoswork.tallybook.datadomain.base.restful;

import org.apache.commons.lang3.NotImplementedException;

/**
 * {@link EntityAction}
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
    private static final String ID = "\\{id\\}";
    private static final String FIELD = "\\{field\\}";
    private static final String ITEM = "\\{item\\}";

    public final static class EntityUris {
        private static final String L_ENTITY_CREATE = "/{entity}/add";              //view add page: RequestMethod.GET, do add : RequestMethod.POST
        private static final String L_ENTITY_READ = "/{entity}/{id}";              //RequestMethod.GET
        private static final String L_ENTITY_UPDATE = "/{entity}/{id}";            //RequestMethod.POST
        private static final String L_ENTITY_DELETE = "/{entity}/{id}/delete";    //RequestMethod.POST
        private static final String L_ENTITY_QUERY = "/{entity}";                   //RequestMethod.GET

        private static final String L_ENTITY_INFO = "/{entity}/info";              //RequestMethod.GET
        private static final String L_ENTITY_SELECT = "/{entity}/select";              //RequestMethod.GET
        private static final String L_ENTITY_TYPEAHEAD = "/{entity}/typeahead";              //RequestMethod.GET

        public static String uriTemplateForAction(String entityName, EntityAction action) {
            String useFullTemplate = null;
            switch (action) {
                case CREATE:
                    useFullTemplate = L_ENTITY_CREATE;
                    break;
                case READ:
                    useFullTemplate = L_ENTITY_READ;
                    break;
                case UPDATE:
                    useFullTemplate = L_ENTITY_UPDATE;
                    break;
                case DELETE:
                    useFullTemplate = L_ENTITY_DELETE;
                    break;
                case QUERY:
                    useFullTemplate = L_ENTITY_QUERY;
                    break;

                case INFO:
                    useFullTemplate = L_ENTITY_INFO;
                    break;
                case SELECT:
                    useFullTemplate = L_ENTITY_SELECT;
                    break;
                case TYPEAHEAD:
                    useFullTemplate = L_ENTITY_TYPEAHEAD;
                    break;
                default:
                    throw new NotImplementedException("Not implemented for " + action);
            }
            return useFullTemplate.replaceFirst(ENTITY, entityName);
        }
    }

    public static final class EntityFieldUris {
        private static final String L_ENTITY_FIELD_INFO = "/{entity}/{field}/info";  //RequestMethod.GET
        private static final String L_ENTITY_FIELD_CREATE = "/{entity}/{field}/add";     //RequestMethod.GET
        private static final String L_ENTITY_FIELD_SELECT = "/{entity}/{field}/select";  //RequestMethod.GET
        private static final String L_ENTITY_FIELD_TYPEAHEAD = "/{entity}/{field}/typeahead";    //RequestMethod.GET
    }

    public static final class BeanFieldUris {
        private static final String _L_BEAN_FIELD_ITEM_PREFIX = "/{entity}/{id}/{field}/";

        //add an entry to the collection-field
        private static final String L_BEAN_FIELD_ITEM_CREATE = "/{entity}/{id}/{field}/add";
        //an entry in the collection-field
        private static final String L_BEAN_FIELD_ITEM_READ = "/{entity}/{id}/{field}/{item}";
        private static final String L_BEAN_FIELD_ITEM_UPDATE = "/{entity}/{id}/{field}/{item}";
        private static final String L_BEAN_FIELD_ITEM_DELETE = "/{entity}/{id}/{field}/{item}/delete";
        //field value of any field type
        private static final String L_BEAN_FIELD_ITEM_QUERY = "/{entity}/{id}/{field}";

        //reorder the collection field
        private static final String L_BEAN_FIELD_ITEM_REORDER = "/{entity}/{id}/{field}/{item}/reorder";

//        //field info
//        private static final String L_BEAN_FIELD_ITEM_INFO = "/{entity}/{id}/{field}/info";
//        //selection field make selection
//        private static final String L_BEAN_FIELD_ITEM_SELECT = "/{entity}/{id}/{field}/select";
//        //selection field make typeahead
//        private static final String L_BEAN_FIELD_ITEM_TYPEAHEAD = "/{entity}/{id}/{field}/typeahead";
//
//        private static final String ENTRY_SELECT = "/{entity}/{id}/{field}/{add_or_item}/{entry_field}/select";

        public static String uriTemplateForCollectionAction(String fieldName, CollectionAction action) {
            String useFullTemplate = null;
            switch (action) {
                case CREATE:
                    useFullTemplate = L_BEAN_FIELD_ITEM_CREATE;
                    break;
                case READ:
                    useFullTemplate = L_BEAN_FIELD_ITEM_READ;
                    break;
                case UPDATE:
                    useFullTemplate = L_BEAN_FIELD_ITEM_UPDATE;
                    break;
                case DELETE:
                    useFullTemplate = L_BEAN_FIELD_ITEM_DELETE;
                    break;
                case QUERY:
                    useFullTemplate = L_BEAN_FIELD_ITEM_QUERY;
                    return "";
                case REORDER:
                    useFullTemplate = L_BEAN_FIELD_ITEM_REORDER;
                    break;
                default:
                    throw new NotImplementedException("Not implemented for " + action);
            }
            return useFullTemplate.substring(_L_BEAN_FIELD_ITEM_PREFIX.length()).replaceFirst(FIELD, fieldName);
        }
    }


}

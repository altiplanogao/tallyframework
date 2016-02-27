package com.taoswork.tallybook.descriptor.description.descriptor.field.typed;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntityHelper;
import com.taoswork.tallybook.datadomain.base.restful.EntityAction;
import com.taoswork.tallybook.datadomain.base.restful.EntityActionPaths;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class ForeignKeyFieldInfo extends BasicFieldInfoBase {
    public final String entityType;
    public final String selectUri;
    public final String recordUri;
    public final String idFieldName;
    public final String displayFieldName;

    public ForeignKeyFieldInfo(String name, String friendlyName, boolean editable,
                               Class declareType, Class entityType,
                               String idFieldName, String displayFieldName) {
        super(name, friendlyName, editable);
        this.entityType = entityType.getName();
        String entityName = PersistEntityHelper.getEntityName(declareType);
        this.selectUri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.SELECT);
        this.recordUri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.READ);
        this.idFieldName = idFieldName;
        this.displayFieldName = displayFieldName;
    }
}

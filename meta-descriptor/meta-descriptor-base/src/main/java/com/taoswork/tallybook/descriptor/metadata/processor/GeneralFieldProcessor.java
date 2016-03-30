package com.taoswork.tallybook.descriptor.metadata.processor;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.MultiMetaHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.*;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.basics.BasicFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.embedded.EmbeddedFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list.ListFieldHandlerDispatcher;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list._ArrayFieldHandler;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.map.MapFieldHandlerDispatcher;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class GeneralFieldProcessor
        extends MultiMetaHandler<Field, FieldMetaMediate>
        implements IFieldProcessor {

    private IClassProcessor parentClassProcessor;
    private BasicFieldHandler basicFieldHandler;
    private ListFieldHandlerDispatcher listFieldHandler;
    private MapFieldHandlerDispatcher mapFieldHandler;
    private final List<IFieldHandler> additionalBasicHandlers = new ArrayList<IFieldHandler>();

    public GeneralFieldProcessor() {
    }

    @Override
    public void setParentClassProcessor(IClassProcessor classProcessor) {
        parentClassProcessor = classProcessor;
    }

    @Override
    public void init() {
        basicFieldHandler = new BasicFieldHandler();
        listFieldHandler = new ListFieldHandlerDispatcher();
        mapFieldHandler = new MapFieldHandlerDispatcher();

        metaHandlers.add(new BeginFieldMetaFieldHandler());
        metaHandlers.add(new PersistAnnotationFieldHandler());
        metaHandlers.add(new PresentationAnnotationFieldHandler());

        addVendorPersistSupport();
        metaHandlers.add(new EmbeddedFieldHandler(parentClassProcessor));
        metaHandlers.add(basicFieldHandler);
        metaHandlers.add(new _ArrayFieldHandler());
        metaHandlers.add(listFieldHandler);
        metaHandlers.add(mapFieldHandler);
        metaHandlers.add(new PaleFieldHandler());

        metaHandlers.add(new EndFieldMetaFieldHandler());

        addBasicFieldHandler(basicFieldHandler);
        addListFieldHandler(listFieldHandler);
        addMapFieldHandler(mapFieldHandler);
    }

    protected void addVendorPersistSupport() {
    }

    protected void addBasicFieldHandler(BasicFieldHandler basicFieldHandler){
    }

    protected void addListFieldHandler(ListFieldHandlerDispatcher listFieldHandler){
    }

    protected void addMapFieldHandler(MapFieldHandlerDispatcher mapFieldHandler){
    }
}

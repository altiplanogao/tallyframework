package com.taoswork.tallybook.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.descriptor.metadata.GroupMeta;
import com.taoswork.tallybook.descriptor.metadata.TabMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IClassHandler;
import com.taoswork.tallybook.descriptor.metadata.utils.FriendlyNameHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class PresentationAnnotationClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationAnnotationClassHandler.class);

    public PresentationAnnotationClassHandler() {
    }

    @Override
    public ProcessResult process(Class<?> clz, MutableClassMeta mutableClassMetadata) {
        PresentationClass presentationClass = clz.getAnnotation(PresentationClass.class);
        if (presentationClass == null) {
            presentationClass = Mork.class.getAnnotation(PresentationClass.class);
        }
        handleAnnotationPresentationClassTabs(presentationClass, mutableClassMetadata);
        handleAnnotationPresentationClassGroups(presentationClass, mutableClassMetadata);
        handleAnnotationPresentationClassFieldOverrides(presentationClass, mutableClassMetadata);
        return ProcessResult.HANDLED;
    }

    private void handleAnnotationPresentationClassFieldOverrides(PresentationClass presentationClass, MutableClassMeta mutableClassMetadata) {
        PresentationClass.FieldOverride fieldOverrides[] = presentationClass.fieldOverrides();
        Map<String, PresentationField> foMap = mutableClassMetadata.getPresentationFieldOverrides();
        for (PresentationClass.FieldOverride fo : fieldOverrides){
            String fieldName = fo.fieldName();
            PresentationField pf = fo.define();
            foMap.put(fieldName, pf);
        }
    }

    private void handleAnnotationPresentationClassTabs(PresentationClass presentationClass, MutableClassMeta mutableClassMetadata) {
        Map<String, TabMeta> tabMetadataMap = mutableClassMetadata.getRWTabMetadataMap();
        Class<?> entityClz = mutableClassMetadata.getEntityClz();
        PresentationClass.Tab[] tabs = presentationClass.tabs();
        for (PresentationClass.Tab tab : tabs) {
            TabMeta tabMeta = new TabMeta();
            tabMeta.setOrder(tab.order())
                    .setName(tab.name())
                    .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassTab(entityClz, tab.name()));
            if (tabMetadataMap.containsKey(tabMeta.getName())) {
                LOGGER.error("TabMeta with name '{}' already exist.", tabMeta.getName());
            }
            tabMetadataMap.put(tabMeta.getName(), tabMeta);
        }
    }

    private void handleAnnotationPresentationClassGroups(PresentationClass presentationClass, MutableClassMeta mutableClassMetadata) {
        Map<String, GroupMeta> groupMetadataMap = mutableClassMetadata.getRWGroupMetadataMap();
        Class<?> entityClz = mutableClassMetadata.getEntityClz();
        PresentationClass.Group[] groups = presentationClass.groups();
        for (PresentationClass.Group group : groups) {
            GroupMeta groupMeta = new GroupMeta();
            groupMeta.setOrder(group.order())
                    .setName(group.name())
                    .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassGroup(entityClz, group.name()));
            if (groupMetadataMap.containsKey(groupMeta.getName())) {
                LOGGER.error("GroupMeta with name '{}' already exist.", groupMeta.getName());
            }
            groupMetadataMap.put(groupMeta.getName(), groupMeta);
        }
    }

    @PresentationClass
    private static class Mork {
    }
}
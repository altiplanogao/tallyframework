package com.taoswork.tallybook.dynamic.datameta.metadata.processor.handler.classes;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.GroupMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.TabMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.datameta.metadata.utils.FriendlyNameHelper;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class PresentationAnnotationClassHandler implements IClassHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationAnnotationClassHandler.class);

    @Override
    public ProcessResult process(Class<?> clz, ClassMetadata classMetadata) {
        PresentationClass presentationClass = clz.getAnnotation(PresentationClass.class);
        if (presentationClass == null) {
            presentationClass = Mork.class.getAnnotation(PresentationClass.class);
        }
        handleAnnotationPresentationClassTabs(presentationClass, classMetadata);
        handleAnnotationPresentationClassGroups(presentationClass, classMetadata);
        return ProcessResult.HANDLED;
    }

    private void handleAnnotationPresentationClassTabs(PresentationClass presentationClass, ClassMetadata classMetadata) {
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getRWTabMetadataMap();
        Class<?> entityClz = classMetadata.getEntityClz();
        PresentationClass.Tab[] tabs = presentationClass.tabs();
        for (PresentationClass.Tab tab : tabs) {
            TabMetadata tabMetadata = new TabMetadata();
            tabMetadata.setOrder(tab.order())
                .setName(tab.name())
                .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassTab(entityClz, tab.name()));
            if (tabMetadataMap.containsKey(tabMetadata.getName())) {
                LOGGER.error("TabMetadata with name '{}' already exist.", tabMetadata.getName());
            }
            tabMetadataMap.put(tabMetadata.getName(), tabMetadata);
        }
    }

    private void handleAnnotationPresentationClassGroups(PresentationClass presentationClass, ClassMetadata classMetadata) {
        Map<String, GroupMetadata> groupMetadataMap = classMetadata.getRWGroupMetadataMap();
        Class<?> entityClz = classMetadata.getEntityClz();
        PresentationClass.Group[] groups = presentationClass.groups();
        for (PresentationClass.Group group : groups) {
            GroupMetadata groupMetadata = new GroupMetadata();
            groupMetadata.setOrder(group.order())
                .setName(group.name())
                .setFriendlyName(FriendlyNameHelper.makeFriendlyName4ClassGroup(entityClz, group.name()));
            if (groupMetadataMap.containsKey(groupMetadata.getName())) {
                LOGGER.error("GroupMetadata with name '{}' already exist.", groupMetadata.getName());
            }
            groupMetadataMap.put(groupMetadata.getName(), groupMetadata);
        }
    }

    @PresentationClass
    private static class Mork {
    }
}
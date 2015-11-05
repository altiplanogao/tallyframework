package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.impl.NamedInfoRW;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.IFieldInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * RawEntityInfo represent for a class information, but having no direct relationship with a specified class/class-tree
 * thus, it can contain information of multiple classes or just a part of a class.
 *
 * RawEntityInfo stores information in following structure:
 *
 *.RawEntityInfo{
 *     basic info
 *     tab info map: {
 *          tab_1: groups {
 *              group_1 : group info { fields },
 *              group_2 : group info { fields }
 *              group_3 : group info { fields }
 *          },
 *          tab_2 : groups {
 *
 *          }
 *          ...
 *     }
 *     fields map: {
 *          field_1: info,
 *          field_2: info,
 *          field_3: info,
 *     }
 *     grid fields: {
 *          grid_1, grid_2, grid_3, grid_4
 *     }
 * }
 *
 */
interface RawEntityInfo
    extends NamedInfoRW, Serializable {

    //special fields
    String getIdField();

    void setIdField(String idField);

    String getNameField();

    void setNameField(String nameField);

    String getPrimarySearchField();

    //field
    void addField(IFieldInfo fieldInfo);

    IFieldInfo getField(String fieldName);

    Map<String, IFieldInfo> getFields();

    //Tab
    void addTab(RawTabInfo tabInfo);

    RawTabInfo getTab(String tabName);

    Collection<? extends RawTabInfo> getTabs();

    //grid
    void addGridField(String fieldName);

    Collection<String> getGridFields();

    //main
    void finishWriting();

}

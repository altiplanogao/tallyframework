package com.taoswork.tallybook.dynamic.datameta.description.builder;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.NamedInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * RawEntityInsight represent for a class information, but having no direct relationship with a specified class/class-tree
 * thus, it can contain information of multiple classes or just a part of a class.
 *
 * RawEntityInsight stores information in following structure:
 *
 *.RawEntityInsight{
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
interface RawEntityInsight
    extends NamedInfo, Serializable {

    FieldInfo getField(String fieldName);

    Map<String, FieldInfo> getFields();


    RawTabInsight getTab(String tabName);

    Collection<? extends RawTabInsight> getTabs();


    Collection<String> getGridFields();

    String getIdField();

    String getNameField();

    String getPrimarySearchField();
}

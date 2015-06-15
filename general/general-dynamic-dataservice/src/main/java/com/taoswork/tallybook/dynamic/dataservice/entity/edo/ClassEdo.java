package com.taoswork.tallybook.dynamic.dataservice.entity.edo;

import com.taoswork.tallybook.dynamic.dataservice.entity.edo.exception.EdoException;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.StringUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ClassEdo extends FriendlyEdo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassEdo.class);

    private FieldEdo idField = null;
    private final Set<TabEdo> tabs = new TreeSet<TabEdo>(new FriendlyEdo.OrderedComparator());
    private final Set<FieldEdo> gridFields = new TreeSet<FieldEdo>(new FriendlyEdo.OrderedComparator());

    public FieldEdo getIdField() {
        return idField;
    }

    public void setIdField(FieldEdo idField) {
        if (null != this.idField) {
            String error = "IdField already set for class '" + this.getName() + "'.";
            LOGGER.error(error);
            throw new EdoException(error);
        }
        this.idField = idField;
    }

    public TabEdo getTab(final String tabName) {
        return CollectionUtility.find(tabs, new TPredicate<TabEdo>() {
            @Override
            public boolean evaluate(TabEdo notNullObj) {
                return notNullObj.getName().equals(tabName);
            }
        });
    }

    public FieldEdo getPrimaryGridSearchField(){
        FieldEdo nameFieldEdo = null;
        FieldEdo firstFieldEdo = null;
        for(FieldEdo fieldEdo : getGridFields()){
            if(firstFieldEdo == null){
                firstFieldEdo = fieldEdo;
            }
            if(fieldEdo.isNameField()){
                return fieldEdo;
            }
            if(fieldEdo.getName().toLowerCase().equals("name")){
                nameFieldEdo = fieldEdo;
            }
        }
        if(null!= nameFieldEdo){
            return nameFieldEdo;
        }
        return firstFieldEdo;
    }

    public Set<FieldEdo> getGridFields() {
        return Collections.unmodifiableSet(gridFields);
    }

    public FieldEdo getGridField(final String fieldName) {
        return CollectionUtility.find(gridFields, new TPredicate<FieldEdo>() {
            @Override
            public boolean evaluate(FieldEdo notNullObj) {
                return notNullObj.getName().equals(fieldName);
            }
        });
    }

    ClassEdo addTab(TabEdo tabDto) {
        TabEdo existingTabDto = getTab(tabDto.getName());
        if (existingTabDto != null) {
            LOGGER.warn("TabEdo '{}' under ClassEdo '{}' already exist.", tabDto.getName(), this.getName());
        }
        tabs.add(tabDto);
        return this;
    }

    ClassEdo addGridField(FieldEdo fieldEdo) {
        FieldEdo existingGridFieldEdo = getGridField(fieldEdo.getName());
        if (existingGridFieldEdo != null) {
            LOGGER.warn("FieldEdo '{}' under ClassEdo '{}' already exist.", fieldEdo.getName(), this.getName());
        }
        gridFields.add(fieldEdo);
        return this;
    }

    public Set<TabEdo> getTabs() {
        return Collections.unmodifiableSet(tabs);
    }

    @Override
    protected String toStringChildInfo() {
        return "tabs.size=" + tabs.size() + ", gridfields.size=" + gridFields.size();
    }

    public void merge(ClassEdo classEdo) {
        if (this.equals(classEdo)) {
            return;
        }
        if(!StringUtility.lastPiece(classEdo.getName(), ".")
                .startsWith(StringUtility.lastPiece(getName(), "."))) {
            LOGGER.warn("Merge ClassEdo with not so similar name: '{}' vs '{}'", getName(), classEdo.getName());
        }
//        if (!CompareUtility.isSame(getFriendlyName(), classEdo.getFriendlyName())) {
//            LOGGER.warn("Merge ClassEdo with different friendly name: '{}' vs '{}'", getFriendlyName(), classEdo.getFriendlyName());
//        }
        for (TabEdo tabEdo : classEdo.tabs) {
            final String tabEdoName = tabEdo.getName();
            TabEdo existionTabEdo = CollectionUtility.find(tabs, new TPredicate<TabEdo>() {
                @Override
                public boolean evaluate(TabEdo notNullObj) {
                    return notNullObj.getName().equals(tabEdoName);
                }
            });
            if (existionTabEdo != null) {
                existionTabEdo.merge(tabEdo);
            } else {
                tabs.add(tabEdo);
            }
        }
        for(FieldEdo fieldEdo : classEdo.gridFields){
            this.gridFields.add(fieldEdo);
        }
    }
}

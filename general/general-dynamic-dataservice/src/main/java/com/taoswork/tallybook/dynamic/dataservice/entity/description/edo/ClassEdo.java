package com.taoswork.tallybook.dynamic.dataservice.entity.description.edo;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.inf.FriendlyEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.exception.EdoException;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.StringUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class ClassEdo extends FriendlyEdo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassEdo.class);

    private String idField = null;
    private String primarySearchField;

    private final Map<String, FieldEdo> fields = new HashMap<String, FieldEdo>();
    private final Set<TabEdo> tabs = new TreeSet<TabEdo>(new FriendlyEdo.OrderedComparator());
    private final Set<OrderedName> gridFieldNames = new TreeSet<OrderedName>(new OrderedName.OrderedComparator());

    ClassEdo addField(FieldEdo fieldEdo) {
        FieldEdo existingFieldEdo = fields.getOrDefault(fieldEdo.getName(), null);
        if (existingFieldEdo != null) {
            LOGGER.warn("FieldEdo '{}' under ClassEdo '{}' already exist.", fieldEdo.getName(), this.getName());
        }
        fields.put(fieldEdo.getName(), fieldEdo);
        return this;
    }

    ClassEdo addTab(TabEdo tabDto) {
        TabEdo existingTabDto = getTab(tabDto.getName());
        if (existingTabDto != null) {
            LOGGER.warn("TabEdo '{}' under ClassEdo '{}' already exist.", tabDto.getName(), this.getName());
        }
        tabs.add(tabDto);
        return this;
    }

    ClassEdo addGridFieldName(OrderedName fieldName) {
        gridFieldNames.add(fieldName);
        return this;
    }

    public Map<String, FieldEdo> getFields(){
        return Collections.unmodifiableMap(fields);
    }

    public FieldEdo getField(final String fieldName) {
        FieldEdo fieldEdo = fields.getOrDefault(fieldName, null);
        return fieldEdo;
    }

    public Set<TabEdo> getTabs() {
        return Collections.unmodifiableSet(tabs);
    }

    public TabEdo getTab(final String tabName) {
        return CollectionUtility.find(tabs, new TPredicate<TabEdo>() {
            @Override
            public boolean evaluate(TabEdo notNullObj) {
                return notNullObj.getName().equals(tabName);
            }
        });
    }

    public Set<OrderedName> getGridFieldsNames() {
        return Collections.unmodifiableSet(gridFieldNames);
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        if (null != this.idField) {
            String error = "IdField already set for class '" + this.getName() + "'.";
            LOGGER.error(error);
            throw new EdoException(error);
        }
        this.idField = idField;
    }

    public String getPrimarySearchField() {
        return primarySearchField;
    }

    public ClassEdo setPrimarySearchField(String primarySearchField) {
        this.primarySearchField = primarySearchField;
        return this;
    }

    FieldEdo calculatePrimaryGridSearchField() {
        FieldEdo nameFieldEdo = null;
        FieldEdo firstFieldEdo = null;
        for (OrderedName fieldName : gridFieldNames) {
            FieldEdo fieldEdo = fields.getOrDefault(fieldName.getName(), null);
            if (fieldEdo == null) {
                continue;
            }
            if (firstFieldEdo == null) {
                firstFieldEdo = fieldEdo;
            }
            if (fieldEdo.isNameField()) {
                return fieldEdo;
            }
            if (fieldEdo.getName().toLowerCase().equals("name")) {
                nameFieldEdo = fieldEdo;
            }
        }
        if (null != nameFieldEdo) {
            return nameFieldEdo;
        }
        return firstFieldEdo;
    }

    public void merge(ClassEdo classEdo) {
        if (this.equals(classEdo)) {
            return;
        }
        if (!StringUtility.lastPiece(classEdo.getName(), ".")
                .startsWith(StringUtility.lastPiece(getName(), "."))) {
            LOGGER.warn("Merge ClassEdo with not so similar name: '{}' vs '{}'", getName(), classEdo.getName());
        }
//        if (!CompareUtility.isSame(getFriendlyName(), classEdo.getFriendlyName())) {
//            LOGGER.warn("Merge ClassEdo with different friendly name: '{}' vs '{}'", getFriendlyName(), classEdo.getFriendlyName());
//        }
        for (Map.Entry<String, FieldEdo> fieldEdoEntry : classEdo.fields.entrySet()) {
            String key = fieldEdoEntry.getKey();
            FieldEdo value = fieldEdoEntry.getValue();
            FieldEdo existingFieldEdo = fields.getOrDefault(key, null);
            if (existingFieldEdo == null) {
                fields.put(key, value);
            } else {
                existingFieldEdo.merge(value);
            }
        }
        for (TabEdo tabEdo : classEdo.tabs) {
            final String tabEdoName = tabEdo.getName();
            TabEdo existingTabEdo = CollectionUtility.find(tabs, new TPredicate<TabEdo>() {
                @Override
                public boolean evaluate(TabEdo notNullObj) {
                    return notNullObj.getName().equals(tabEdoName);
                }
            });
            if (existingTabEdo != null) {
                existingTabEdo.merge(tabEdo);
            } else {
                tabs.add(tabEdo);
            }
        }
        for (OrderedName fieldName : classEdo.gridFieldNames) {
            this.gridFieldNames.add(fieldName);
        }
    }

    @Override
    protected String toStringChildInfo() {
        return "tabs.size=" + tabs.size() + ", gridfields.size=" + gridFieldNames.size();
    }
}

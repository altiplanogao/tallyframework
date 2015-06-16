package com.taoswork.tallybook.dynamic.dataservice.query.dto;

/**
 * Created by Gao Yuan on 2015/6/15.
 */
public abstract class PropertyCriteria {


    protected String propertyName;

    public PropertyCriteria(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public PropertyCriteria setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }


    /** Example url:
     * Assume we have an rest api for 'telephone' resources:
     * We can search resources using:
     * http://localhost/telephones
     *
     * Specify filter:
     * http://localhost/telephones?brand=cisco
     *
     * Specify order:
     * http://localhost/telephones?sort_brand=ASCENDING
     *
     * Specify filter and order:
     * http://localhost/telephones?brand=cisco&sort_brand=ASCENDING
     *
     * Specify multi-filter
     * http://localhost/telephones?brand=cisco&color=black
     *
     * Specify multi-filter-and-order
     * http://localhost/telephones?brand=cisco&color=black&sort_brand=ASCENDING&sort_color=DESCENDING
     *
     */
}

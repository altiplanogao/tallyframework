package com.taoswork.tallybook.general.datadomain.support.presentation.client;

/**
 * Created by Gao Yuan on 2015/7/1.
 */
public enum FieldType {
    //More Specified
    ID,
    NAME,
    EMAIL,
    PHONE,
    PASSWORD,
    PASSWORD_CONFIRM,
    CODE,
    FOREIGN_KEY,

    //Less Specified
    BOOLEAN,
    STRING,
    DATE,
    INTEGER,
    DECIMAL,
    URL,
    COLOR,

    //Others
    ADDITIONAL_FOREIGN_KEY,
    ENUMERATION,
    EXPLICIT_ENUMERATION,
    EMPTY_ENUMERATION,
    DATA_DRIVEN_ENUMERATION,
    HTML,
    HTML_BASIC,
    UPLOAD,
    HIDDEN,
    ASSET_URL,
    ASSET_LOOKUP,
    MEDIA,
    RULE_SIMPLE,
    RULE_WITH_QUANTITY,
    STRING_LIST,
    IMAGE,

    //Default
    UNKNOWN,

}

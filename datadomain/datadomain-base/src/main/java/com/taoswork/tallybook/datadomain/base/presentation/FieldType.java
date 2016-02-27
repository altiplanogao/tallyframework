package com.taoswork.tallybook.datadomain.base.presentation;

/**
 * Created by Gao Yuan on 2015/7/1.
 */
public enum FieldType {
    //String
    ID,         //the value could be in any class, such as Long.class, but we treat it as string like
    NAME,       //display as string
    EMAIL,       //display as string
    PHONE,       //display as string
    PASSWORD,
    PASSWORD_CONFIRM,
    STRING,
    URL,
    HTML,
    HTML_BASIC,

    //Featured simple
    BOOLEAN,    //has additional info, such as whether display as "YES/NO" or "TRUE/FALSE"
    ENUMERATION,    //Has additional info: Enum type. see ({@link com.taoswork.tallybook.general.extension.utils.IFriendlyEnum})
    CODE,       //like enum, but has more detailed information
    MONEY,      //has currency info

    //More Specified
    FOREIGN_KEY,            //foreign key entity
    EXTERNAL_FOREIGN_KEY,  //foreign key entity in different data source

    EMBEDDABLE,
    COLLECTION,
    MAP,

    //Less Specified
    DATE,
    INTEGER,
    DECIMAL,
    COLOR,

    //Others
    ADDITIONAL_FOREIGN_KEY,
    EXPLICIT_ENUMERATION,
    EMPTY_ENUMERATION,
    DATA_DRIVEN_ENUMERATION,
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

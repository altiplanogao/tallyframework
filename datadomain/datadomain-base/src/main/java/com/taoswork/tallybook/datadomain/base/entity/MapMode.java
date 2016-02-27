package com.taoswork.tallybook.datadomain.base.entity;

/**
 * Map version of CollectionMode
 */
public enum MapMode {
    Sloth,       //only perform very basic operation: such as: copy, do not support ui data translation

    Basic,      //Value Is Basic      //2.1 (A.2)
    Entity,     //Owning Entity With Key   //2.2 (B.2)// new entity, one-many relation
    Lookup,     //Lookup Entity With Key  //2.3 (C.2 case a)
    AdornedLookup //Adorned Entity Without Key //2.3 (C.2 case b)
}

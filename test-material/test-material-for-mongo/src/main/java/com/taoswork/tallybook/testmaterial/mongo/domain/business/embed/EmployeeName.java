package com.taoswork.tallybook.testmaterial.mongo.domain.business.embed;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class EmployeeName {
    private String first_Name;
    private String last_Name;
// ...
}
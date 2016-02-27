package com.taoswork.tallybook.testmaterial.mongo.domain.business.embed;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Calendar;

@Embedded
public class VacationEntry {
    private Calendar startDate;
    private int daysTaken;
}
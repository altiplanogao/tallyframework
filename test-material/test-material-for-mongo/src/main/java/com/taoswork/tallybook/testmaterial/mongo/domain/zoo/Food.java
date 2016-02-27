package com.taoswork.tallybook.testmaterial.mongo.domain.zoo;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Food {
    FoodType type;
    String name;
    String description;
}

package com.taoswork.tallybook.testmaterial.mongo.domain.common;

import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class Address {
    @PresentationField(visibility = Visibility.GRID_HIDE)
    private String street;

    private String city;

    private String state;

    private String zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
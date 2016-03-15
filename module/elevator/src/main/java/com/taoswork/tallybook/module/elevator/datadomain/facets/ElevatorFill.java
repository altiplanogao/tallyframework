package com.taoswork.tallybook.module.elevator.datadomain.facets;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public class ElevatorFill extends BaseAssetFacetFill {
    public int totalFloors;
    public String brand;

    public int getTotalFloors() {
        return totalFloors;
    }

    public void setTotalFloors(int totalFloors) {
        this.totalFloors = totalFloors;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

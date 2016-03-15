package com.taoswork.tallybook.module.elevator.datadomain.facets;

import java.math.BigDecimal;

/**
 * Created by Gao Yuan on 2016/3/10.
 */
public class EscalatorFill extends BaseAssetFacetFill {
    public int stairs;
    public String engineModel;
    public String handrailMaterial;
    public BigDecimal lengthInMeter;

    public int getStairs() {
        return stairs;
    }

    public void setStairs(int stairs) {
        this.stairs = stairs;
    }

    public String getEngineModel() {
        return engineModel;
    }

    public void setEngineModel(String engineModel) {
        this.engineModel = engineModel;
    }

    public String getHandrailMaterial() {
        return handrailMaterial;
    }

    public void setHandrailMaterial(String handrailMaterial) {
        this.handrailMaterial = handrailMaterial;
    }

    public BigDecimal getLengthInMeter() {
        return lengthInMeter;
    }

    public void setLengthInMeter(BigDecimal lengthInMeter) {
        this.lengthInMeter = lengthInMeter;
    }
}

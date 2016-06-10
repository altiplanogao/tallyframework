package com.taoswork.tallybook.business.datadomain.tallymanagement;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateCellMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationDate;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
@Entity("moduleentry")
@PersistEntity("moduleentry")
public class ModuleEntry extends AbstractDocument {

    protected String name;
    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String fullName;

    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String description;

    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String moduleService; // a key for module service url

//    protected List<String> assetFacets;
    protected String producer;
    protected ManagementPrivacy privacy  = ManagementPrivacy.Public;

    protected String version;

    @PersistField(fieldType = FieldType.DATE)
    @PresentationDate(mode = DateMode.Date, cellMode = DateCellMode.Date)
    protected Date updateDate;

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected String publicKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleService() {
        return moduleService;
    }

    public void setModuleService(String moduleService) {
        this.moduleService = moduleService;
    }

//    public List<String> getAssetFacets() {
//        return assetFacets;
//    }
//
//    public void setAssetFacets(List<String> assetFacets) {
//        this.assetFacets = assetFacets;
//    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public ManagementPrivacy getPrivacy() {
        return privacy;
    }

    public void setPrivacy(ManagementPrivacy privacy) {
        this.privacy = privacy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

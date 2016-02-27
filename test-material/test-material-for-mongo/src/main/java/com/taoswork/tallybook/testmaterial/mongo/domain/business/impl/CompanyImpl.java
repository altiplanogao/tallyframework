package com.taoswork.tallybook.testmaterial.mongo.domain.business.impl;


import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistField;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallybook.datadomain.base.presentation.PresentationField;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationBoolean;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.StringEntry;
import com.taoswork.tallybook.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.ICompany;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.enumtype.CompanyType;
import com.taoswork.tallybook.testmaterial.mongo.domain.common.Address;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@PresentationClass(
        tabs = {
                @PresentationClass.Tab(name = CompanyImpl.Presentation.Tab.General, order = 1),
                @PresentationClass.Tab(name = CompanyImpl.Presentation.Tab.Marketing, order = 2),
                @PresentationClass.Tab(name = CompanyImpl.Presentation.Tab.Contact, order = 3)
        },
        groups = {
                @PresentationClass.Group(name = CompanyImpl.Presentation.Group.General, order = 1),
                @PresentationClass.Group(name = CompanyImpl.Presentation.Group.Advanced, order = 2),
                @PresentationClass.Group(name = CompanyImpl.Presentation.Group.Public, order = 3),
                @PresentationClass.Group(name = CompanyImpl.Presentation.Group.Private)
        }
)
public class CompanyImpl extends AbstractDocument implements ICompany {
    /**
     * ************** General Tab ******************
     * --- General Group ---
     * : Name
     * : Description
     * : Description2
     * --- Advanced Group ---
     * : Creation Date
     * --- Private Group ---
     * : Tax code
     * : AdminId
     * <p>
     * ************** Marketing Tab *****************
     * --- Public Group ---
     * : PublicProducts - List
     * <p>
     * --- Private Group ---
     * : PrivateProducts - List
     * : BusinessPartners - List
     * <p>
     * ************* Contact Tab *******************
     * --- General Group ---
     * : email
     * : phone
     * : address - Embedded Address
     * :
     */

    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.General)
    protected String name;

    protected Long asset;

    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.General)
    protected String description;

    protected String description2;

    protected transient String handyMemo1;
    protected
    @Transient
    String handyMemo2;

    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Advanced,
            visibility = Visibility.GRID_HIDE
    )
    protected Date creationDate;

    @PersistField(fieldType = FieldType.ENUMERATION)
    @PresentationField(visibility = Visibility.VISIBLE_ALL)
    @PresentationEnum(enumeration = CompanyType.class)
    private CompanyType companyType;

    @PresentationField
    @PresentationBoolean(mode = BooleanMode.YesNo)
    protected boolean locked = true;

    @PresentationField
    @PresentationBoolean(mode = BooleanMode.TrueFalse)
    protected Boolean active = true;

    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Private,
            visibility = Visibility.GRID_HIDE
    )
    protected String taxCode;

    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Private
    )
    protected Long adminId;

    @PresentationField(
            tab = Presentation.Tab.Marketing,
            group = Presentation.Group.Public
    )
    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
    protected List<String> publicProducts = new ArrayList<String>();

    @PresentationField(
            tab = Presentation.Tab.Marketing,
            group = Presentation.Group.Private
    )
    @CollectionField(mode = CollectionMode.Primitive)
    protected List<String> privateProducts = new ArrayList<String>();

    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    protected String email;

    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    protected String phone;

    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    private Address address = new Address();

    @Override
    public Long getAsset() {
        return asset;
    }

    @Override
    public void setAsset(Long asset) {
        this.asset = asset;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription2() {
        return description2;
    }

    @Override
    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    @Override
    public String getHandyMemo1() {
        return handyMemo1;
    }

    @Override
    public void setHandyMemo1(String handyMemo1) {
        this.handyMemo1 = handyMemo1;
    }

    @Override
    public String getHandyMemo2() {
        return handyMemo2;
    }

    @Override
    public void setHandyMemo2(String handyMemo2) {
        this.handyMemo2 = handyMemo2;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public CompanyType getCompanyType() {
        return companyType;
    }

    @Override
    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public Boolean isActive() {
        return active;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String getTaxCode() {
        return taxCode;
    }

    @Override
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    @Override
    public Long getAdminId() {
        return adminId;
    }

    @Override
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @Override
    public List<String> getPublicProducts() {
        return publicProducts;
    }

    @Override
    public void setPublicProducts(List<String> publicProducts) {
        this.publicProducts = publicProducts;
    }

    @Override
    public List<String> getPrivateProducts() {
        return privateProducts;
    }

    @Override
    public void setPrivateProducts(List<String> privateProducts) {
        this.privateProducts = privateProducts;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    public static class Presentation {
        public static class Tab {
            public static final String General = "General";
            public static final String Marketing = "Marketing";
            public static final String Contact = "Contact";
        }

        public static class Group {
            public static final String General = "General";
            public static final String Advanced = "Advanced";
            public static final String Public = "Public";
            public static final String Private = "Private";
        }
    }

}

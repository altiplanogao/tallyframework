package com.taoswork.tallybook.dynamic.datameta.testdata.clazzes;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.enumtype.CompanyType;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.EnumField;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
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
public class CompanyImpl implements ICompany{
    /**
     *  ************** General Tab ******************
     *  --- General Group ---
     *  : Name
     *  : Description
     *  : Description2
     *  --- Advanced Group ---
     *  : Creation Date
     *  --- Private Group ---
     *  : Tax code
     *  : AdminId
     *
     *  ************** Marketing Tab *****************
     *  --- Public Group ---
     *  : PublicProducts - List
     *
     *  --- Private Group ---
     *  : PrivateProducts - List
     *  : BusinessPartners - List
     *
     *  ************* Contact Tab *******************
     *  --- General Group ---
     *  : email
     *  : phone
     *  : address - Embedded Address
     *  :
     */

    @Id
    @Column(name = "ID")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.General)
    protected Long id;

    @Column(name = "NAME")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.General)
    protected String name;

    @Column(name = "DESCRIPTION")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.General)
    protected String description;

    protected String description2;

    @Column(name = "CREATION_DATE")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Advanced,
            visibility = Visibility.GRID_HIDE
    )
    protected Date creationDate;

    @Column(name = "TYPE")
    @PresentationField(visibility = Visibility.VISIBLE_ALL, fieldType = FieldType.ENUMERATION)
    @EnumField(enumeration = CompanyType.class)
    private String companyType;

    @Column(name = "TAX_CODE")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Private,
            visibility = Visibility.GRID_HIDE
    )
    protected String taxCode;

    @Column(name = "ADMIN_ID")
    @PresentationField(
            tab = Presentation.Tab.General,
            group = Presentation.Group.Private
    )
    protected Long adminId;

    @PresentationField(
            tab = Presentation.Tab.Marketing,
            group = Presentation.Group.Public
    )
    protected List<ProductImpl> publicProducts;

    @PresentationField(
            tab = Presentation.Tab.Marketing,
            group = Presentation.Group.Private
    )
    protected List<ProductImpl> privateProducts;

    @PresentationField(
            tab = Presentation.Tab.Marketing,
            group = Presentation.Group.Private
    )
    protected List<BusinessPartnerImpl> businessPartners;

    @Column(name = "EMAIL")
    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    protected String email;

    @Column(name = "PHONE")
    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    protected String phone;

    @Embedded
    @PresentationField(
            tab = Presentation.Tab.Contact,
            group = Presentation.Group.General
    )
    protected AddressImpl address;

    public static class Presentation{
        public static class Tab{
            public static final String General = "General";
            public static final String Marketing = "Marketing";
            public static final String Contact = "Contact";
        }
        public static class Group{
            public static final String General = "General";
            public static final String Advanced = "Advanced";
            public static final String Public = "Public";
            public static final String Private = "Private";
        }
    }

}

package com.taoswork.tallybook.dataservice;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface IDataServiceDelegate {
    IDataServiceDefinition getDataServiceDefinition();

    IDataService theDataService();
}

package com.taoswork.tallybook.dynamic.datameta.metadata.friendly;

public interface IFriendlyOrdered extends IFriendly {

    int getOrder();

    IFriendlyOrdered setOrder(int order);
}

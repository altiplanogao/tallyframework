package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.server.io.request.GeneralRequestParameter;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import com.taoswork.tallybook.general.extension.utils.UriUtility;
import gumi.builders.UrlBuilder;
import org.springframework.hateoas.Link;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class LinkBuilder {
    public static void buildLinkForQueryResults(String fullRequestUrl, EntityQueryResponse response){
        QueryResultRange currentRange = response.getEntities().makeRange();
        QueryResultRange next = currentRange.next();
        QueryResultRange pre = currentRange.pre();
        response.add(new Link(fullRequestUrl));
        if(pre.isValid()){
            UrlBuilder urlBuilder = UrlBuilder.fromString(fullRequestUrl);
            if(pre.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, ""+pre.getStart());
            }else{
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_PREVIOUS));
        }
        if(next.isValid()){
            UrlBuilder urlBuilder = UrlBuilder.fromString(fullRequestUrl);
            if(next.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, ""+next.getStart());
            }else{
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_NEXT));
        }

    }

    public static void buildLinkForReadResults(String fullUrl, EntityReadResponse response) {
        response.add(new Link(fullUrl));
        response.add(new Link(UriUtility.findParent(fullUrl)).withRel("search"));
    }
}

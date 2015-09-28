package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.dynamic.dataservice.server.io.EntityActionPaths;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.GeneralRequestParameter;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityCreateFreshResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import com.taoswork.tallybook.general.extension.utils.UriUtility;
import gumi.builders.UrlBuilder;
import org.springframework.hateoas.Link;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class LinkBuilder {

    public static void buildLinkForInfoResults(String fullUrl, EntityResponse response) {
        response.add(new Link(fullUrl));

    }

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
        appendEntityLinks(UrlBuilder.fromString(fullRequestUrl).withParameters(null).toString(), response);
    }

    public static void buildLinkForReadResults(String fullUrl, EntityReadResponse response) {
        response.add(new Link(fullUrl));
        String entityUrl = UriUtility.findParent(fullUrl);
        entityUrl = (entityUrl.endsWith("/")? entityUrl.substring(0, entityUrl.length() - 1) : entityUrl);
        appendEntityLinks(UrlBuilder.fromString(entityUrl).withParameters(null).toString(), response);
        appendEntityInstanceLinks(fullUrl, response);
    }

    public static void buildLinkForNewInstanceResults(String fullUrl, EntityCreateFreshResponse response) {
        response.add(new Link(fullUrl));
    }
    /**
     *
     * @param entityUrl, url with entitytype path, example: http://localhost:2222/xxx
     * @param response
     */
    private static void appendEntityLinks (String entityUrl, EntityResponse response){
        {   //search
            response.add(new Link(entityUrl).withRel(EntityActionNames.SEARCH));
        }
        {   //add
            UrlBuilder urlBuilder = UrlBuilder.fromString(entityUrl).withParameters(null);
            String path = urlBuilder.path + EntityActionPaths.ADD;
            urlBuilder.withPath(path).toString();
            response.add(new Link(urlBuilder.withPath(path).toString()).withRel(EntityActionNames.ADD));
        }
        {   //inspect
            UrlBuilder urlBuilder = UrlBuilder.fromString(entityUrl).withParameters(null);
            String path = urlBuilder.path + EntityActionPaths.INSPECT;
            urlBuilder.withPath(path).toString();
            response.add(new Link(urlBuilder.withPath(path).toString()).withRel(EntityActionNames.INSPECT));
        }
    }

    private static void appendEntityInstanceLinks (String entityObjectUrl,  EntityResponse response){
        {   //update
            response.add(new Link(entityObjectUrl).withRel(EntityActionNames.UPDATE));
        }
        {   //delete
            UrlBuilder urlBuilder = UrlBuilder.fromString(entityObjectUrl).withParameters(null);
            String path = urlBuilder.path + EntityActionPaths.DELETE;
            urlBuilder.withPath(path).toString();
            response.add(new Link(urlBuilder.withPath(path).toString()).withRel(EntityActionNames.DELETE));
        }
    }

}

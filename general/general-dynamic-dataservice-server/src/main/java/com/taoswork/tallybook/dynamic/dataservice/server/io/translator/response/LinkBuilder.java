package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.dynamic.dataservice.server.io.EntityActionPaths;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityCreateFreshResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import gumi.builders.UrlBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.hateoas.Link;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class LinkBuilder {

    public static void buildLinkForInfoResults(EntityInfoRequest request, EntityResponse response) {
        response.add(new Link(request.getFullUri()));
    }

    public static void buildLinkForQueryResults(EntityQueryRequest request, EntityQueryResponse response) {
        appendEntityActionUris(request.getResourceName(), response);
        if (response.getErrors().containsError())
            return;

        QueryResultRange currentRange = response.getEntities().makeRange();
        QueryResultRange next = currentRange.next();
        QueryResultRange pre = currentRange.pre();
        response.add(new Link(request.getFullUri()));
        if (pre.isValid()) {
            UrlBuilder urlBuilder = UrlBuilder.fromString(request.getFullUri());
            if (pre.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, "" + pre.getStart());
            } else {
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_PREVIOUS));
        }
        if (next.isValid()) {
            UrlBuilder urlBuilder = UrlBuilder.fromString(request.getFullUri());
            if (next.getStart() != 0) {
                urlBuilder = urlBuilder.setParameter(GeneralRequestParameter.REQUEST_START_INDEX, "" + next.getStart());
            } else {
                urlBuilder = urlBuilder.removeParameters(GeneralRequestParameter.REQUEST_START_INDEX);
            }
            response.add(new Link(urlBuilder.toString()).withRel(Link.REL_NEXT));
        }
    }

    public static void buildLinkForReadResults(EntityReadRequest request, EntityReadResponse response) {
        response.add(new Link(request.getFullUri()));
        appendEntityActionUris(request.getResourceName(), response);
    }

    public static void buildLinkForNewInstanceResults(EntityCreateFreshRequest request, EntityCreateFreshResponse response) {
        response.add(new Link(request.getFullUri()));
    }

    public static String buildLinkForReadInstance(String entityName){
        return EntityActionPaths.uriTemplateForRead(entityName);
    }
    /**
     *
     * @param entityName, url with entitytype path, example: http://localhost:2222/{entityName}
     * @param response
     */
    private static void appendEntityActionUris (String entityName, EntityResponse response){
        {   //inspect
            String uri = EntityActionPaths.uriTemplateForInfo(entityName);
            response.add(new Link(uri).withRel(EntityActionNames.INSPECT));
        }
        {   //search
            response.add(new Link(EntityActionPaths.uriTemplateForQuery(entityName)).withRel(EntityActionNames.QUERY));
        }
        {   //create
            String uri = EntityActionPaths.uriTemplateForAdd(entityName);
            response.add(new Link(uri).withRel(EntityActionNames.CREATE));
        }
        {   //read
            String uri = EntityActionPaths.uriTemplateForRead(entityName);
            response.add(new Link(uri).withRel(EntityActionNames.READ));
        }
        {   //update
            String uri = EntityActionPaths.uriTemplateForUpdate(entityName);
            response.add(new Link(uri).withRel(EntityActionNames.UPDATE));
        }
        {   //delete
            String uri = EntityActionPaths.uriTemplateForDelete(entityName);
            response.add(new Link(uri).withRel(EntityActionNames.DELETE));
        }
    }

}

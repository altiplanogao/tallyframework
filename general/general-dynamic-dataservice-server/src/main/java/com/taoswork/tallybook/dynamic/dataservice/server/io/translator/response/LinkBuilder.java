package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.datadomain.restful.EntityAction;
import com.taoswork.tallybook.dynamic.datadomain.restful.EntityActionPaths;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityCreateFreshResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import gumi.builders.UrlBuilder;
import org.springframework.hateoas.Link;

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
        return EntityActionPaths.EntityUris.uriTemplateForAction(entityName, EntityAction.READ);
    }
    /**
     *
     * @param entityName, url with entitytype path, example: http://localhost:2222/{entityName}
     * @param response
     */
    private static void appendEntityActionUris (String entityName, EntityResponse response){
        {   //inspect
            EntityAction action = EntityAction.INFO;
            String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
            response.add(new Link(uri).withRel(action.getType()));
        }
        {   //search
            EntityAction action = EntityAction.QUERY;
            response.add(new Link(EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action))
                .withRel(action.getType()));
        }
        {   //create
            EntityAction action = EntityAction.CREATE;
            String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
            response.add(new Link(uri).withRel(action.getType()));
        }
        {   //read
            EntityAction action = EntityAction.READ;
            String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
            response.add(new Link(uri).withRel(action.getType()));
        }
        {   //update
            EntityAction action = EntityAction.UPDATE;
            String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
            response.add(new Link(uri).withRel(action.getType()));
        }
        {   //delete
            EntityAction action = EntityAction.DELETE;
            String uri = EntityActionPaths.EntityUris.uriTemplateForAction(entityName, action);
            response.add(new Link(uri).withRel(action.getType()));
        }
    }

}

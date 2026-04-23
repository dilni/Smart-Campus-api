package com.smartcampus.resource;

import com.smartcampus.model.ApiMetadata;
import com.smartcampus.model.Link;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
public class DiscoveryResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ApiMetadata getDiscovery() {
        ApiMetadata metadata = new ApiMetadata("v1", "Smart Campus API");
        
        String baseUri = uriInfo.getBaseUri().toString();
        
        metadata.addLink(new Link("self", baseUri, "GET"));
        metadata.addLink(new Link("rooms", baseUri + "rooms", "GET, POST"));
        metadata.addLink(new Link("sensors", baseUri + "sensors", "GET, POST"));
        
        return metadata;
    }
}

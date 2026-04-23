package com.smartcampus.resource;

import com.smartcampus.repository.InMemoryDataStore;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/admin")
public class AdminResource {

    @DELETE
    @Path("/reset")
    public Response reset() {
        InMemoryDataStore.clearAll();
        return Response.noContent().build();
    }
}


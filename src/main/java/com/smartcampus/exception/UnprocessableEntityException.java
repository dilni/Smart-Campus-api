package com.smartcampus.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MaintenanceForbiddenExceptionMapper implements ExceptionMapper<MaintenanceForbiddenException> {
    @Override
    public Response toResponse(MaintenanceForbiddenException exception) {
        ErrorMessage error = new ErrorMessage(
            Response.Status.FORBIDDEN.getStatusCode(),
            "Forbidden",
            exception.getMessage()
        );
        return Response.status(Response.Status.FORBIDDEN)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

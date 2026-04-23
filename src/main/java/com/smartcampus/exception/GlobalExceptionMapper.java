package com.smartcampus.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        // If it's already a WebApplicationException (e.g. 404), extract its status
        if (exception instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) exception;
            Response response = webEx.getResponse();
            
            // Re-wrap standard exceptions to ensure no HTML stack trace is leaked
            ErrorMessage error = new ErrorMessage(
                response.getStatus(),
                response.getStatusInfo().getReasonPhrase(),
                exception.getMessage() != null ? exception.getMessage() : "An error occurred"
            );
            
            return Response.status(response.getStatus())
                    .entity(error)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // Catch-all 500 for unhandled exceptions
        ErrorMessage error = new ErrorMessage(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            "Internal Server Error",
            "An unexpected error occurred. Please try again later."
        );

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

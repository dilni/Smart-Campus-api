package com.smartcampus.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnprocessableEntityExceptionMapper implements ExceptionMapper<UnprocessableEntityException> {
    @Override
    public Response toResponse(UnprocessableEntityException exception) {
        ErrorMessage error = new ErrorMessage(
            422,
            "Unprocessable Entity",
            exception.getMessage()
        );
        return Response.status(422)
                .entity(error)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

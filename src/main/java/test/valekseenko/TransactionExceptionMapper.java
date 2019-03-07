package test.valekseenko;

import test.valekseenko.domain.ExceptionMessage;
import test.valekseenko.exceptions.TransactionServiceException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionExceptionMapper implements ExceptionMapper<TransactionServiceException> {

    @Override
    public Response toResponse(TransactionServiceException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ExceptionMessage(e))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

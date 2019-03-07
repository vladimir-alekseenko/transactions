package test.valekseenko.endpoints;

import com.google.inject.Inject;
import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.exceptions.*;
import test.valekseenko.services.TransactionService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transactions")
public class TransactionEndpoint {

    private TransactionService transactionService;

    @Inject
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionsList() {
        List<Transaction> transactionsList = transactionService.getTransactionList();
        return Response.ok(transactionsList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionDetails(@PathParam("id") long id) throws TransactionServiceException {
        Transaction transaction = transactionService.getTransaction(id);
        return Response.ok(transaction).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction(TransactionDetails transactionDetails) throws OverlimitAmountException, AccountNotFoundException, NonSufficientFundsException, NegativeTransactionValueException {
        Transaction transaction = transactionService.createTransaction(transactionDetails);
        return Response.ok(transaction).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveTransaction(@PathParam("id") long id) throws TransactionServiceException {
        Transaction transaction = transactionService.approveTransaction(id);
        return Response.ok(transaction).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response declineTransaction(@PathParam("id") long id) throws InvalidTransactionStatusException, TransactionNotFoundException {
        Transaction transaction = transactionService.declineTransaction(id);
        return Response.ok(transaction).build();
    }
}

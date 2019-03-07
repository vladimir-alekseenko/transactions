package test.valekseenko;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.valekseenko.domain.ExceptionMessage;
import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.Assert.*;

public class ServerTest {

    private static UndertowJaxrsServer server;

    @BeforeClass
    public static void init() {
        server = new UndertowJaxrsServer().start();
        server.deploy(JaxrsApplication.class);
    }

    @AfterClass
    public static void stop() {
        server.stop();
    }

    @Test
    public void testGetTransactionList() {
        Client client = ClientBuilder.newClient();
        TransactionDetails transactionDetails = new TransactionDetails(1002L, 1003L, 2000.00d);
        Entity entity = Entity.entity(transactionDetails, MediaType.APPLICATION_JSON);
        client.target(TestPortProvider.generateURL("/api/transactions")).request().post(entity);
        Response response = client.target(TestPortProvider.generateURL("/api/transactions")).request().get();
        assertEquals(200,response.getStatus());
        assertFalse(response.readEntity(new GenericType<List<Transaction>>() {}).isEmpty());
    }

    @Test
    public void testFailedRequest() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(TestPortProvider.generateURL("/api/transactions/0")).request().get();
        assertEquals(400,response.getStatus());
        ExceptionMessage exceptionMessage = response.readEntity(ExceptionMessage.class);
        assertNotNull(exceptionMessage);
        assertNotNull(exceptionMessage.getMessage());
        assertNotNull(exceptionMessage.getError());
    }
}
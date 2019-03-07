package test.valekseenko.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.BeforeClass;
import org.junit.Test;
import test.valekseenko.GuiceModule;
import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.domain.TransactionStatus;
import test.valekseenko.exceptions.InvalidTransactionStatusException;
import test.valekseenko.exceptions.TransactionNotFoundException;

import java.util.List;

import static org.junit.Assert.*;

public class TransactionDAOTest {

    private static Injector injector = Guice.createInjector(new GuiceModule());
    private static TransactionDAO transactionDAO;

    @BeforeClass
    public static void init() {
        transactionDAO = injector.getInstance(TransactionDAO.class);
        transactionDAO.createTransaction(new TransactionDetails(1002L,1003L, 100.00d));
        transactionDAO.createTransaction(new TransactionDetails(1003L, 1002L, 100.00d));
    }

    @Test
    public void testCreateTransaction() {
        TransactionDetails transactionDetails= new TransactionDetails(1002L,1003L, 200.00d);
        transactionDAO.createTransaction(transactionDetails);
        Transaction transaction = null;
        try {
            transaction = transactionDAO.getTransaction(3L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(3L, transaction.getId());
        assertEquals(TransactionStatus.OPENED, transaction.getStatus());
        assertEquals(transactionDetails, transaction.getDetails());
    }

    @Test
    public void testTransactionList() {
        List<Transaction> transactionList = transactionDAO.getTransactionList();
        assertEquals(1L,transactionList.get(0).getId());
        assertEquals(2L,transactionList.get(1).getId());
    }

    @Test
    public void testGetTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionDAO.getTransaction(1L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(1L, transaction.getId());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testGetNonExistentTransaction() throws TransactionNotFoundException {
        transactionDAO.getTransaction(0L);
    }


    @Test
    public void testApproveTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionDAO.approveTransaction(1L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(transaction.getStatus(),TransactionStatus.COMPLETED);

        transaction=null;
        try {
            transaction = transactionDAO.getTransaction(1L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(transaction.getStatus(),TransactionStatus.COMPLETED);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testApproveNonExistentTransaction() throws TransactionNotFoundException {
        transactionDAO.approveTransaction(0L);
    }

    @Test
    public void testCancelTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionDAO.declineTransaction(2L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(transaction.getStatus(),TransactionStatus.CANCELED);

        transaction=null;
        try {
            transaction = transactionDAO.getTransaction(2L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(transaction.getStatus(),TransactionStatus.CANCELED);
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testCancelNonExistentTransaction() throws TransactionNotFoundException {
        transactionDAO.declineTransaction(0L);
    }
}
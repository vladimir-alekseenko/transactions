package test.valekseenko.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.BeforeClass;
import org.junit.Test;
import test.valekseenko.GuiceModule;
import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.domain.TransactionStatus;
import test.valekseenko.exceptions.*;

import java.util.List;

import static org.junit.Assert.*;

public class TransactionServiceTest {

    private static Injector injector = Guice.createInjector(new GuiceModule());
    private static TransactionService transactionService;

    @BeforeClass
    public static void init() {
        transactionService = injector.getInstance(TransactionService.class);
        try {
            transactionService.createTransaction(new TransactionDetails(1001L, 1002L, 1000.00d));
            transactionService.createTransaction(new TransactionDetails(1002L, 1003L, 1000.00d));
            transactionService.createTransaction(new TransactionDetails(1003L, 1001L, 1000.00d));
            transactionService.createTransaction(new TransactionDetails(1001L, 1003L, 1000.00d));
            transactionService.createTransaction(new TransactionDetails(1002L, 1001L, 1000.00d));
            transactionService.createTransaction(new TransactionDetails(1003L, 1002L, 1000.00d));
        } catch (OverlimitAmountException | NonSufficientFundsException | AccountNotFoundException | NegativeTransactionValueException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetTransactionList() {
        List<Transaction> transactionList = transactionService.getTransactionList();
        assertFalse(transactionList.isEmpty());
    }

    @Test
    public void testGetTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionService.getTransaction(1L);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(1L,transaction.getId());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testGetNonExistentTransaction() throws TransactionNotFoundException {
        transactionService.getTransaction(0L);
    }


    @Test
    public void testCreateTransaction() {
        TransactionDetails transactionDetails = new TransactionDetails(1002L, 1001L, 1000.00d);
        Transaction transaction = null;
        try {
            transaction = transactionService.createTransaction(transactionDetails);
        } catch (OverlimitAmountException | NonSufficientFundsException | AccountNotFoundException | NegativeTransactionValueException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        long id = transaction.getId();

        transaction = null;
        try {
            transaction = transactionService.getTransaction(id);
        } catch (TransactionNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(id, transaction.getId());
        assertEquals(transactionDetails, transaction.getDetails());
    }

    @Test(expected = OverlimitAmountException.class)
    public void testCreateOverlimitTransaction() throws OverlimitAmountException, AccountNotFoundException, NonSufficientFundsException, NegativeTransactionValueException {
        transactionService.createTransaction(new TransactionDetails(1001L,1002L, 20000.00d));
    }

    @Test(expected = AccountNotFoundException.class)
    public void testCreateNonExistentAccountTransaction() throws OverlimitAmountException, AccountNotFoundException, NonSufficientFundsException, NegativeTransactionValueException {
        transactionService.createTransaction(new TransactionDetails(1000L,1001L, 1000.00d));
    }

    @Test(expected = NonSufficientFundsException.class)
    public void testCreateOverAccountBalanceTransaction() throws OverlimitAmountException, AccountNotFoundException, NonSufficientFundsException, NegativeTransactionValueException {
        transactionService.createTransaction(new TransactionDetails(1003L,1002L, 9000.00d));
    }

    @Test(expected = NegativeTransactionValueException.class)
    public void testCreateNegativeValueTransaction() throws OverlimitAmountException, AccountNotFoundException, NonSufficientFundsException, NegativeTransactionValueException {
        transactionService.createTransaction(new TransactionDetails(1001L,1002L, -1000.00d));
    }

    @Test
    public void testApproveTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionService.approveTransaction(4L);
        } catch (TransactionServiceException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testApproveNonExistentTransaction() throws TransactionServiceException {
        transactionService.approveTransaction(0L);
    }

    @Test(expected = InvalidTransactionStatusException.class)
    public void testApproveDeclinedTransaction() throws TransactionServiceException {
        transactionService.declineTransaction(5L);
        transactionService.approveTransaction(5L);
    }

    @Test(expected = InvalidTransactionStatusException.class)
    public void testApproveCompletedTransaction() throws TransactionServiceException {
        transactionService.approveTransaction(6L);
        transactionService.approveTransaction(6L);
    }

    @Test
    public void testApproveExpiredTransaction() {
        //TODO implement
    }

    @Test
    public void testDeclineTransaction() {
        Transaction transaction = null;
        try {
            transaction = transactionService.declineTransaction(7L);
        } catch (TransactionServiceException e) {
            e.printStackTrace();
        }
        assertNotNull(transaction);
        assertEquals(TransactionStatus.CANCELED,transaction.getStatus());
    }

    @Test(expected = TransactionNotFoundException.class)
    public void testDeclineNonExistentTransaction() throws InvalidTransactionStatusException, TransactionNotFoundException {
        transactionService.declineTransaction(0L);
    }

    @Test(expected = InvalidTransactionStatusException.class)
    public void testDeclineCompletedTransaction() throws TransactionServiceException {
        transactionService.approveTransaction(8L);
        transactionService.declineTransaction(8L);
    }

    @Test(expected = InvalidTransactionStatusException.class)
    public void testDeclineDeclinedTransaction() throws InvalidTransactionStatusException, TransactionNotFoundException {
        transactionService.declineTransaction(9L);
        transactionService.declineTransaction(9L);
    }
}
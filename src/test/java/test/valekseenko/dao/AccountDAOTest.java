package test.valekseenko.dao;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.BeforeClass;
import org.junit.Test;
import test.valekseenko.GuiceModule;
import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NonSufficientFundsException;

import static org.junit.Assert.*;

public class AccountDAOTest {

    private static Injector injector = Guice.createInjector(new GuiceModule());
    private static AccountDAO accountDAO;

    @BeforeClass
    public static void init() {
        accountDAO = injector.getInstance(AccountDAO.class);
    }

    @Test
    public void testInitialAccounts() {
        double firstAccountBalance = 0.00d;
        double secondAccountBalance = 0.00d;
        double thirdAccountBalance = 0.00d;
        try {
            firstAccountBalance = accountDAO.getBalance(1001L);
            secondAccountBalance = accountDAO.getBalance(1002L);
            thirdAccountBalance = accountDAO.getBalance(1003L);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(40000.00d,firstAccountBalance, 0.01d);
        assertEquals(10000.00d,secondAccountBalance, 0.01d);
        assertEquals(1000.00d,thirdAccountBalance, 0.01d);
    }

    @Test
    public void testWithdrawal() {
        double firstAccountBalance = 0.00d;
        try {
            accountDAO.withdraw(1001L, 10000.00d);
            firstAccountBalance = accountDAO.getBalance(1001L);
        } catch (AccountNotFoundException | NonSufficientFundsException e) {
            e.printStackTrace();
        }
        assertEquals(30000.00d, firstAccountBalance, 0.01d);
        try {
            accountDAO.deposit(1001L, 10000.00d);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeposit() {
        double firstAccountBalance = 0d;
        try {
            accountDAO.deposit(1001L, 10000.00d);
            firstAccountBalance = accountDAO.getBalance(1001L);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(50000.00d, firstAccountBalance, 0.01d);
        try {
            accountDAO.withdraw(1001L, 10000.00d);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        } catch (NonSufficientFundsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = AccountNotFoundException.class)
    public void testGetBalanceNotExistentAccount() throws AccountNotFoundException {
        accountDAO.getBalance(1004L);
    }

    @Test(expected = NonSufficientFundsException.class)
    public void testWithdrawOverBalance() throws NonSufficientFundsException, AccountNotFoundException {
        accountDAO.withdraw(1003L,10000.00d);
    }
}
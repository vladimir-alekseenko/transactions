package test.valekseenko.services;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.BeforeClass;
import org.junit.Test;
import test.valekseenko.GuiceModule;
import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NegativeTransactionValueException;
import test.valekseenko.exceptions.NonSufficientFundsException;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private static Injector injector = Guice.createInjector(new GuiceModule());
    private static AccountService accountService;

    @BeforeClass
    public static void init() {
        accountService = injector.getInstance(AccountService.class);
    }

    @Test
    public void testAccountExists() {
        assertTrue(accountService.accountExists(1001L));
        assertTrue(accountService.accountExists(1002L));
        assertTrue(accountService.accountExists(1003L));
        assertFalse(accountService.accountExists(1000L));
    }

    @Test
    public void testGetBalance() {
        double balance = 0.00;
        try {
            balance = accountService.getBalance(1002L);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(10000.00d, balance, 0.01d);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testNonExistentAccountGetBalance() throws AccountNotFoundException {
        accountService.getBalance(1000L);
    }

    @Test
    public void testWithdraw() {
        double balance = 0.00d;
        try {
            accountService.withdraw(1001L, 5000.00d);
            balance = accountService.getBalance(1001L);
        } catch (AccountNotFoundException | NonSufficientFundsException e) {
            e.printStackTrace();
        }
        assertEquals(35000.00d, balance, 0.01d);
        try {
            accountService.deposit(1001L, 5000.00d);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = AccountNotFoundException.class)
    public void testNonExistentAccountWithdraw() throws NonSufficientFundsException, AccountNotFoundException {
        accountService.withdraw(1000L, 1000.00d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmountWithdraw() throws NonSufficientFundsException, AccountNotFoundException {
        accountService.withdraw(1001L, -1000.00d);
    }

    @Test(expected = NonSufficientFundsException.class)
    public void testOverLimitWithdraw() throws NonSufficientFundsException, AccountNotFoundException {
        accountService.withdraw(1003L, 10000.00d);
    }

    @Test
    public void testDeposit() {
        double balance = 0.00d;
        try {
            accountService.deposit(1002L, 5000.00d);
            balance = accountService.getBalance(1002L);
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(15000.00d, balance, 0.01d);
        try {
            accountService.withdraw(1002L, 5000.00d);
        } catch (NonSufficientFundsException e) {
            e.printStackTrace();
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = AccountNotFoundException.class)
    public void testNonExistentAccountDeposit() throws AccountNotFoundException {
        accountService.deposit(1000L, 1000.00d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmountDeposit() throws AccountNotFoundException {
        accountService.deposit(1001L, -1000.00d);
    }
}
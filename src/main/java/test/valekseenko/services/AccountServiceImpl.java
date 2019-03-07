package test.valekseenko.services;

import com.google.inject.Inject;
import test.valekseenko.dao.AccountDAO;
import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NonSufficientFundsException;

public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO;

    @Inject
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public boolean accountExists(long id) {
        return accountDAO.accountExists(id);
    }

    @Override
    public Double getBalance(long id) throws AccountNotFoundException {
        return accountDAO.getBalance(id);
    }

    @Override
    public void withdraw(long id, double amount) throws NonSufficientFundsException, AccountNotFoundException {
        if (amount > 0.00d) {
            accountDAO.withdraw(id, amount);
        } else throw new IllegalArgumentException();
    }

    @Override
    public void deposit(long id, double amount) throws AccountNotFoundException {
        if (amount > 0.00d) {
            accountDAO.deposit(id, amount);
        } else throw new IllegalArgumentException();
    }
}

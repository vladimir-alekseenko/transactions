package test.valekseenko.dao;

import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NonSufficientFundsException;

public interface AccountDAO {
    boolean accountExists(long id);
    void withdraw(long id, double amount) throws AccountNotFoundException, NonSufficientFundsException;
    void deposit(long id, double amount) throws AccountNotFoundException;
    Double getBalance(long id) throws AccountNotFoundException;
}

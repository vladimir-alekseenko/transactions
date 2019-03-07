package test.valekseenko.services;

import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NonSufficientFundsException;

public interface AccountService {
    boolean accountExists(long id);
    Double getBalance(long id) throws AccountNotFoundException;
    void withdraw(long id, double amount) throws NonSufficientFundsException, AccountNotFoundException;
    void deposit(long id, double amount) throws AccountNotFoundException;
}

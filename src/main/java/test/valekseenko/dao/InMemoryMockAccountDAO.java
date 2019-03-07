package test.valekseenko.dao;

import test.valekseenko.exceptions.AccountNotFoundException;
import test.valekseenko.exceptions.NonSufficientFundsException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMockAccountDAO implements AccountDAO {

    private  static Map<Long, Double> accounts = new ConcurrentHashMap<>();

    public InMemoryMockAccountDAO() {
        accounts.put(1001L, 40000.00);
        accounts.put(1002L, 10000.00);
        accounts.put(1003L, 1000.00);
    }

    public boolean accountExists(long id) {
        return accounts.containsKey(id);
    }

    private boolean hasBalance(long id, double amount) throws AccountNotFoundException {
        if (accountExists(id)) {
            return accounts.get(id) >= amount;
        } else throw new AccountNotFoundException("Account id="+id+" not found");
    }

    @Override
    public synchronized void withdraw(long id, double amount) throws AccountNotFoundException, NonSufficientFundsException {
        if (accountExists(id)) {
            if (hasBalance(id, amount)) {
                accounts.put(id, accounts.get(id) - amount);
            } else throw new NonSufficientFundsException("Non-sufficient funds");
        } else throw new AccountNotFoundException("Account id="+id+" not found");
    }

    @Override
    public synchronized void deposit(long id, double amount) throws AccountNotFoundException {
        if (accountExists(id)) {
            accounts.put(id, accounts.get(id) + amount);
        } else throw new AccountNotFoundException("Account id="+id+" not found");
    }

    @Override
    public Double getBalance(long id) throws AccountNotFoundException {
        if (accountExists(id)) {
            return accounts.get(id);
        } else throw new AccountNotFoundException("Account id="+id+" not found");
    }
}

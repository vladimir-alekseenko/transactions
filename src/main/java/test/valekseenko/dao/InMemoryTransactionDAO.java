package test.valekseenko.dao;

import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.domain.TransactionStatus;
import test.valekseenko.exceptions.TransactionNotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTransactionDAO implements TransactionDAO {

    private static Map<Long, Transaction> transactions = new ConcurrentHashMap<>();
    private static AtomicLong id = new AtomicLong(0);

    @Override
    public List<Transaction> getTransactionList() {
        return new ArrayList<Transaction>(transactions.values());
    }

    @Override
    public Transaction getTransaction(long id) throws TransactionNotFoundException {
        if (transactions.containsKey(id)) {
            return transactions.get(id);
        } throw new TransactionNotFoundException("Transaction id="+id+" not found");
    }

    @Override
    public Transaction createTransaction(TransactionDetails transactionDetails) {
        long id = this.id.incrementAndGet();
        Transaction transaction = new Transaction(id,
                new Date(),
                TransactionStatus.OPENED,
                transactionDetails);
        transactions.put(id,transaction);
        return transaction;
    }

    @Override
    public Transaction approveTransaction(long id) throws TransactionNotFoundException {
        if (transactions.containsKey(id)) {
            Transaction transaction = transactions.get(id);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactions.put(id, transaction);
            return transaction;
        } else throw new TransactionNotFoundException("Transaction id="+id+" not found");
    }

    @Override
    public Transaction declineTransaction(long id) throws TransactionNotFoundException {
        if (transactions.containsKey(id)) {
            Transaction transaction = transactions.get(id);
            transaction.setStatus(TransactionStatus.CANCELED);
            transactions.put(id, transaction);
            return transaction;
        } else throw new TransactionNotFoundException("Transaction id="+id+" not found");
    }
}

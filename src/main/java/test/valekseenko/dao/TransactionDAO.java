package test.valekseenko.dao;

import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.exceptions.TransactionNotFoundException;

import java.util.List;

public interface TransactionDAO {
    List<Transaction> getTransactionList();
    Transaction getTransaction(long id) throws TransactionNotFoundException;
    Transaction createTransaction(TransactionDetails transactionDetails);
    Transaction approveTransaction(long id) throws TransactionNotFoundException;
    Transaction declineTransaction(long id) throws TransactionNotFoundException;
}

package test.valekseenko.services;

import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.exceptions.*;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactionList();
    Transaction getTransaction(long id) throws TransactionNotFoundException;
    Transaction createTransaction(TransactionDetails transactionDetails) throws OverlimitAmountException, NonSufficientFundsException, AccountNotFoundException, NegativeTransactionValueException;
    Transaction approveTransaction(long id) throws TransactionServiceException;
    Transaction declineTransaction(long id) throws TransactionNotFoundException, InvalidTransactionStatusException;
}

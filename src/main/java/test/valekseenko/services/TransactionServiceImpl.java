package test.valekseenko.services;

import com.google.inject.Inject;
import test.valekseenko.Constants;
import test.valekseenko.dao.TransactionDAO;
import test.valekseenko.domain.Transaction;
import test.valekseenko.domain.TransactionDetails;
import test.valekseenko.domain.TransactionStatus;
import test.valekseenko.exceptions.*;

import java.util.Date;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;
    private AccountService accountService;

    @Inject
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public List<Transaction> getTransactionList() {
        List<Transaction> transactionList = transactionDAO.getTransactionList();
        transactionList.forEach(this::processExpiredTransaction);
        return transactionList;
    }

    @Override
    public Transaction getTransaction(long id) throws TransactionNotFoundException {
        return processExpiredTransaction(transactionDAO.getTransaction(id));
    }

    @Override
    public Transaction createTransaction(TransactionDetails transactionDetails) throws OverlimitAmountException, NonSufficientFundsException, AccountNotFoundException, NegativeTransactionValueException {
        return transactionDAO.createTransaction(validateTransactionDetails(transactionDetails));
    }

    @Override
    public Transaction approveTransaction(long id) throws TransactionServiceException {
        Transaction transaction = processExpiredTransaction(transactionDAO.getTransaction(id));
        long fromId = transaction.getDetails().getFrom();
        long toId = transaction.getDetails().getTo();
        double value = transaction.getDetails().getValue();
        if (transaction.getStatus() == TransactionStatus.OPENED) {
            try {
                accountService.withdraw(fromId,value);
                accountService.deposit(toId,value);
                return transactionDAO.approveTransaction(id);
            } catch (IllegalArgumentException e) {
                throw new TransactionServiceException(e.getMessage());
            }
        } else throw new InvalidTransactionStatusException("Only transactions in status OPENED can be approved");
    }

    @Override
    public Transaction declineTransaction(long id) throws TransactionNotFoundException, InvalidTransactionStatusException {
        Transaction transaction = processExpiredTransaction(transactionDAO.getTransaction(id));
        if (transaction.getStatus() == TransactionStatus.COMPLETED || transaction.getStatus() == TransactionStatus.CANCELED) {
            throw new InvalidTransactionStatusException("Only transactions in status OPENED can be cancelled");
        } else if (transaction.getStatus() == TransactionStatus.OPENED) {
            transaction = transactionDAO.declineTransaction(id);
        }
        return transaction;
    }

    private Transaction processExpiredTransaction(Transaction transaction) {
        if (transaction.getStatus() == TransactionStatus.OPENED) {
            long fiveMinutes = 5 * 60 * 1000;
            if ((new Date().getTime() - transaction.getTimestamp().getTime()) > fiveMinutes) {
                try {
                    transaction = transactionDAO.declineTransaction(transaction.getId());
                } catch (TransactionNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return transaction;
    }

    private TransactionDetails validateTransactionDetails(TransactionDetails transactionDetails) throws AccountNotFoundException, OverlimitAmountException, NonSufficientFundsException, NegativeTransactionValueException {
        long sourceId = transactionDetails.getFrom();
        if (!accountService.accountExists(sourceId)) {
            throw new AccountNotFoundException("Account id="+sourceId+" not found");
        }

        long targetId = transactionDetails.getTo();
        if (!accountService.accountExists(targetId)) {
            throw new AccountNotFoundException("Account id="+sourceId+" not found");
        }

        double transactionValue = transactionDetails.getValue();
        if (transactionValue <= 0.00d) {
            throw new NegativeTransactionValueException("Negative value transactions are not allowed");
        }
        if (transactionValue > Constants.TRANSACTION_LIMIT) {
            throw new OverlimitAmountException("Transaction amount exceeds limit");
        }
        if (transactionValue > accountService.getBalance(sourceId)) {
            throw new NonSufficientFundsException("Non-sufficient funds");
        }
        return transactionDetails;
    }
}

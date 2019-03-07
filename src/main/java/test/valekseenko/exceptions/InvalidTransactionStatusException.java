package test.valekseenko.exceptions;

public class InvalidTransactionStatusException extends TransactionServiceException {
    public InvalidTransactionStatusException(String message) {
        super(message);
    }
}

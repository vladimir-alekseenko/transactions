package test.valekseenko.exceptions;

public class NegativeTransactionValueException extends TransactionServiceException {
    public NegativeTransactionValueException(String message) {
        super(message);
    }
}

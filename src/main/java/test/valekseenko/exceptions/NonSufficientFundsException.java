package test.valekseenko.exceptions;

public class NonSufficientFundsException extends TransactionServiceException {
    public NonSufficientFundsException(String message) {
        super(message);
    }
}

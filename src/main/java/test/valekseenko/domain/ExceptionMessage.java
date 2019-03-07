package test.valekseenko.domain;

import test.valekseenko.exceptions.TransactionServiceException;

import java.util.Date;

public class ExceptionMessage {
    private Date timestamp;
    private String error;
    private String message;

    public ExceptionMessage() {
    }

    public ExceptionMessage(TransactionServiceException e) {
        timestamp = new Date();
        error = e.getClass().getSimpleName();
        message = e.getMessage();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionMessage{" +
                "timestamp=" + timestamp +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

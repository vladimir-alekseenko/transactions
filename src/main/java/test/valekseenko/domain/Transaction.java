package test.valekseenko.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Transaction {

    private long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private TransactionStatus status;
    private TransactionDetails details;

    public Transaction() {
    }

    public Transaction(long id, Date timestamp, TransactionStatus status, TransactionDetails details) {
        this.id = id;
        this.timestamp = timestamp;
        this.status = status;
        this.details = details;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionDetails getDetails() {
        return details;
    }

    public void setDetails(TransactionDetails details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", details=" + details +
                '}';
    }
}

package test.valekseenko.domain;

public class TransactionDetails {

    private long from;
    private long to;
    private double value;

    public TransactionDetails() {
    }

    public TransactionDetails(long from, long to, double value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TransactionDetails{" +
                "from=" + from +
                ", to=" + to +
                ", value=" + value +
                '}';
    }
}

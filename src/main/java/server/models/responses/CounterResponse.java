package server.models.responses;

public class CounterResponse {
    private long counterStatus;

    public CounterResponse (long counterStatus) {
        this.counterStatus = counterStatus;
    }

    public long getCounterStatus() {
        return counterStatus;
    }

    public void setCounterStatus(long counterStatus) {
        this.counterStatus = counterStatus;
    }

}

package server.counter;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private final AtomicLong counter;

    public long get() {
        return counter.get();
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Counter (long initialValue, String name) {
        counter = new AtomicLong(initialValue);
        this.name = name;
    }

    void incrementBy(long value) {
        counter.addAndGet(value);
    }

    void decrementBy(long value) { counter.addAndGet(value*-1); }

}

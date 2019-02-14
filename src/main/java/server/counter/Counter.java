package server.counter;

import java.util.concurrent.atomic.AtomicLong;

public class Counter {

    private final AtomicLong counter;

    public long get() {
        return counter.get();
    }

    public Counter (long initialValue) {
        counter = new AtomicLong(initialValue);
    }

    public long incrementBy(long value) {
        return counter.addAndGet(value);
    }

}

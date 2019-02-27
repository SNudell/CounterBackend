package server.counter;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Optional;

import static server.Constants.*;

@Component
public class CounterDispenser {

    private HashMap<String, Counter> counters;

    public CounterDispenser() {
        counters = loadCounters();
    }

    public HashMap loadCounters() {
        HashMap counters =  new HashMap<String,Counter>();
        counters.put(DEFAULT_COUNTER_IDENTIFIER, new Counter(DEFAULT_STARTING_VALUE));
        return counters;
    }

    public Optional<Counter> getCounter(String name) {
        Optional<Counter> counter = Optional.ofNullable(counters.get(name));
        return counter;

    }

}
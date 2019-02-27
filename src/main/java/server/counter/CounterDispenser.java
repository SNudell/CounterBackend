package server.counter;

import org.springframework.stereotype.Component;
import server.models.requests.CreateCounterRequest;
import server.models.responses.CounterResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        counters.put(DEFAULT_COUNTER_IDENTIFIER, new Counter(DEFAULT_STARTING_VALUE, DEFAULT_COUNTER_IDENTIFIER));
        return counters;
    }

    public Optional<Counter> getCounter(String name) {
        Optional<Counter> counter = Optional.ofNullable(counters.get(name));
        return counter;
    }

    public Counter addCounter(CreateCounterRequest request) { ;
        Counter newCounter = new Counter(request.getValue(), request.getName());
        counters.put(request.getName(),newCounter);
        return newCounter;
    }

    public List<Counter> findAll() {
        List all = new ArrayList<Counter>();
        for (Counter counter : counters.values()) {
            all.add(counter);
        }
        return all;
    }

}
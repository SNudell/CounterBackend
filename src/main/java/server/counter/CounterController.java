package server.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.counter.mongodb.MongoCounterDispenser;
import server.errorHandling.exceptions.NoSuchCounterException;
import server.models.requests.CreateCounterRequest;
import server.models.requests.DecrementCounterRequest;
import server.models.requests.IncrementCounterRequest;

import java.util.List;
import java.util.Optional;

@Component
public class CounterController {

    @Autowired
    MongoCounterDispenser dispenser;

    public CounterController () {}

    public Counter addCounter(CreateCounterRequest request) { ;
        Counter newCounter = new Counter(request.getValue(), request.getName());
        dispenser.addCounter(newCounter);
        return newCounter;
    }

    public List<Counter> findAll() {
        return dispenser.findAll();
    }

    public Counter getCounter(String name) throws NoSuchCounterException {
        Optional<Counter> optionalCounter = dispenser.getCounter(name);
        if (optionalCounter.isEmpty()) {
            throw new NoSuchCounterException(name);
        }
        Counter counter = optionalCounter.get();
        return counter;
    }

    public Counter incrementCounter(IncrementCounterRequest request) throws NoSuchCounterException {
        Counter counter = getCounter(request.getName());
        counter.incrementBy(request.getIncrement());
        return counter;
    }

    public Counter decrementCounter(DecrementCounterRequest request) throws NoSuchCounterException {
        Counter counter = getCounter(request.getName());
        counter.decrementBy(request.getDecrement());
        return counter;
    }

    public void deleteCounter (String name) throws NoSuchCounterException {
        if (!dispenser.exists(name)) {
            throw new NoSuchCounterException(name);
        }
        dispenser.delete(name);
    }
}

package server.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.counter.mongodb.MongoCounterDispenser;
import server.errorHandling.exceptions.CounterAlreadyExistsException;
import server.errorHandling.exceptions.NoSuchCounterException;

import java.util.List;
import java.util.Optional;

@Component
public class CounterController {

    private final MongoCounterDispenser dispenser;

    @Autowired
    public CounterController(MongoCounterDispenser dispenser) {
        this.dispenser = dispenser;
    }

    public Counter addCounter(String name, long initialValue) throws CounterAlreadyExistsException {
        Counter newCounter = new Counter(initialValue, name);
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
        return optionalCounter.get();
    }

    public Counter incrementCounter(String name, long increment) throws NoSuchCounterException {
        Counter counter = getCounter(name);
        counter.incrementBy(increment);
        dispenser.update(counter);
        return counter;
    }

    public Counter decrementCounter(String name, long decrement) throws  NoSuchCounterException {
        Counter counter = getCounter(name);
        counter.decrementBy(decrement);
        dispenser.update(counter);
        return counter;
    }

    public void deleteCounter (String name) throws NoSuchCounterException {
        if (!dispenser.exists(name)) {
            throw new NoSuchCounterException(name);
        }
        dispenser.delete(name);
        System.out.println("deleted " + name);
    }
}

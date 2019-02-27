package server.apiControllers.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.counter.Counter;
import server.counter.CounterDispenser;
import server.errorHandling.exceptions.BadRequestException;
import server.errorHandling.exceptions.NoSuchCounterException;
import server.models.requests.CreateCounterRequest;
import server.models.responses.CounterResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/counter")
public class CounterApiController {

    @Autowired
    CounterDispenser dispenser;

    @GetMapping
    public List<CounterResponse> findAll() {
        System.out.println("received Get will send all counters as response");
        List<Counter> counters = dispenser.findAll();
        return counters.stream().map(counter -> (new CounterResponse(counter))).collect(Collectors.toList());
    }

    @GetMapping(value = "/{name}")
    public CounterResponse findOne(@PathVariable(value = "name") String name) throws NoSuchCounterException{
        Optional<Counter> counter = dispenser.getCounter(name);
        System.out.println("received get with a value = " + name);
        if (counter.isPresent()) {
            // unwrap first the get the value
            return new CounterResponse(counter.get());
        } else {
            throw new NoSuchCounterException(name);
        }
    }

    @PostMapping
    public CounterResponse create(@RequestBody CreateCounterRequest requestBody) throws BadRequestException {
        if (requestBody == null) {
            throw new BadRequestException();
        }
        Counter newCounter = dispenser.addCounter(requestBody);
        System.out.println("Added new Counter " + newCounter.getName());
        return new CounterResponse(newCounter);
    }

}

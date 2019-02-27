package server.apiControllers.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.counter.Counter;
import server.counter.CounterController;
import server.errorHandling.exceptions.BadRequestException;
import server.errorHandling.exceptions.NoSuchCounterException;
import server.models.requests.CreateCounterRequest;
import server.models.requests.DecrementCounterRequest;
import server.models.requests.IncrementCounterRequest;
import server.models.responses.CounterResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/counter")
public class CounterApiController {

    @Autowired
    CounterController controller;

    @GetMapping
    public List<CounterResponse> findAll() {
        System.out.println("received Get will send all counters as response");
        List<Counter> counters = controller.findAll();
        return counters.stream().map(counter -> (new CounterResponse(counter))).collect(Collectors.toList());
    }

    @GetMapping(value = "/{name}")
    public CounterResponse findOne(@PathVariable(value = "name") String name) throws NoSuchCounterException{
        System.out.println("received Get on \""+name+"\"");
        Counter counter = controller.getCounter(name);
        return new CounterResponse(counter);
    }

    @PostMapping
    public CounterResponse create(@RequestBody CreateCounterRequest requestBody) throws BadRequestException {
        checkRequest(requestBody);
        Counter newCounter = controller.addCounter(requestBody);
        System.out.println("Added new Counter " + newCounter.getName());
        return new CounterResponse(newCounter);
    }

    @PutMapping("/increment")
    public CounterResponse increment(@RequestBody IncrementCounterRequest requestBody) throws BadRequestException, NoSuchCounterException {
        checkRequest(requestBody);
        if (requestBody.getIncrement() < 0) {
            throw new BadRequestException("increment must be greater then 0");
        }
        Counter counter = controller.incrementCounter(requestBody);
        return new CounterResponse(counter);
    }

    @PutMapping("/decrement")
    public CounterResponse decrement(@RequestBody DecrementCounterRequest requestBody) throws BadRequestException, NoSuchCounterException {
        checkRequest(requestBody);
        if (requestBody.getDecrement() < 0) {
            throw new BadRequestException("increment must be greater then 0");
        }
        Counter counter = controller.decrementCounter(requestBody);
        return new CounterResponse(counter);
    }

    @DeleteMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCounter (@PathVariable(value = "name") String name) throws NoSuchCounterException {
        controller.deleteCounter(name);
    }

    private void checkRequest(Object request) throws BadRequestException {
        if (request == null) {
            throw new BadRequestException("request body was null");
        }
    }

}

package server.apiControllers.counter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.counter.Counter;
import server.counter.CounterController;
import server.errorHandling.exceptions.BadNameException;
import server.errorHandling.exceptions.BadRequestException;
import server.errorHandling.exceptions.CounterAlreadyExistsException;
import server.errorHandling.exceptions.NoSuchCounterException;
import server.models.requests.CreateCounterRequest;
import server.models.requests.DecrementCounterRequest;
import server.models.requests.IncrementCounterRequest;
import server.models.responses.CounterResponse;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/counter")
public class CounterApiController {

    @Autowired
    CounterController controller;

    @GetMapping
    public List<CounterResponse> findAll() {
        List<Counter> counters = controller.findAll();
        return counters.stream().map(counter -> (new CounterResponse(counter))).collect(Collectors.toList());
    }

    @GetMapping(value = "/{name}")
    public CounterResponse findOne(@PathVariable(value = "name") String name) throws NoSuchCounterException, BadNameException {
        String trimmedName = name.trim();
        isValidName(trimmedName);
        Counter counter = controller.getCounter(trimmedName);
        return new CounterResponse(counter);
    }

    @PostMapping
    public CounterResponse create(@RequestBody CreateCounterRequest requestBody) throws BadRequestException, BadNameException, CounterAlreadyExistsException {
        checkRequest(requestBody);
        if (requestBody.getName().isEmpty()) {
            System.out.println("received empty name");
            throw new BadRequestException("name cannot be empty");
        }
        String trimmedName = requestBody.getName().trim();
        isValidName(trimmedName);
        Counter newCounter = controller.addCounter(trimmedName, requestBody.getValue());
        System.out.println("Added new Counter " + newCounter.getName());
        return new CounterResponse(newCounter);
    }

    @PutMapping("/increment")
    public CounterResponse increment(@RequestBody IncrementCounterRequest requestBody) throws BadRequestException, BadNameException, NoSuchCounterException {
        checkRequest(requestBody);
        if (requestBody.getIncrement() < 0) {
            System.out.println("received decrement too small");
            throw new BadRequestException("increment must be greater then 0");
        }
        String trimmedName = requestBody.getName().trim();
        isValidName(trimmedName);
        Counter counter = controller.incrementCounter(trimmedName, requestBody.getIncrement());
        return new CounterResponse(counter);
    }

    @PutMapping("/decrement")
    public CounterResponse decrement(@RequestBody DecrementCounterRequest requestBody) throws BadRequestException, BadNameException, NoSuchCounterException {
        checkRequest(requestBody);
        if (requestBody.getDecrement() < 0) {
            System.out.println("received decrement too small");
            throw new BadRequestException("increment must be greater then 0");
        }
        String trimmedName = requestBody.getName().trim();
        isValidName(trimmedName);
        Counter counter = controller.decrementCounter(trimmedName, requestBody.getDecrement());
        return new CounterResponse(counter);
    }

    @DeleteMapping(value = "/{name}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCounter (@PathVariable(value = "name") String name) throws NoSuchCounterException, BadNameException {
        String trimmedName = name.trim();
        isValidName(trimmedName);
        controller.deleteCounter(name);
    }

    private void checkRequest(Object request) throws BadRequestException {
        if (request == null) {
            System.out.println("Received empty request");
            throw new BadRequestException("request body was null");
        }
    }

    private boolean isValidName(String name) throws BadNameException {
        for (Character c: name.toCharArray()) {
            if (Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c) || Character.isWhitespace(c)){}
            else {
                System.out.println("Received invalid name " + name);
                throw new BadNameException();
            }
        }
        return true;
    }

}

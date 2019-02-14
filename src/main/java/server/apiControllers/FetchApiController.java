package server.apiControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.counter.Counter;
import server.counter.CounterDispenser;
import server.errorHandling.exceptions.NoSuchCounterException;
import server.models.responses.CounterResponse;
import java.util.Optional;

import static server.Constants.*;

@RestController
public class FetchApiController {

    @Autowired
    CounterDispenser dispenser;

    @RequestMapping(value = "/api/fetch", method=RequestMethod.GET)
    public CounterResponse fetchCounter(@RequestParam(value="name", defaultValue=DEFAULT_COUNTER_IDENTIFIER) String name) throws NoSuchCounterException{
        Optional<Counter> counter = dispenser.getCounter(name);
        if (counter.isPresent()) {
            // unwrap first the get the value
            return new CounterResponse(counter.get().get());
        } else {
            throw new NoSuchCounterException(name);
        }
    }

}

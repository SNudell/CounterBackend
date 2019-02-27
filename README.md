# Basic Server to manage a counter

Basic Spring web REST api that handles a counter.

## get all counters
get request to  /counter

## get a specific counter

get request to /counter/NAME_OF_COUNTER

## create a new counter 
post request to /counter with parameteres 

    {
        "name":"yourName",
        "value":"startingValue"
    }
example curl

    curl -d '{"name":"Counter", "value":42}' -H "Content-Type:application/json"  -X POST http://localhost:8080/counter
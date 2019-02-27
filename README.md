# Basic Server to manage a counter

Basic Spring web REST api that handles an arbitrary amount of counters. These are managed in a MongoDB. Requires mongodb to be installed and added to PATH.

## get all counters
get request to  /counter

## get a specific counter

get request to /counter/NAME_OF_COUNTER

## create a new counter 
post request to /counter.\
JSON Body:

    {
        "name":"nameForTheNewCouter",
        "value":startingValue
    }
example curl:

    curl -d '{"name":"MyCounter", "value":42}' -H "Content-Type:application/json"  -X POST http://localhost:8080/counter    
## modify existing counters
either increment a counter by sending a PUT request to /counter/increment.\
JSON Body:

    {
        "name":"nameOfCounter",
        "increment":incrementValue
    }

example curl:

    curl -d '{"name":"MyCounter", "increment":3}' -H "Content-Type:application/json"  -X PUT http://localhost:8080/counter/increment    

or decrement by sending a PUT request to /counter/decrement.\
JSON Bos<:

    {
        "name":"nameOfCounter",
        "decrement":decrementValue
    }

example curl:

    curl -d '{"name":"MyCounter", "decrement":2}' -H "Content-Type:application/json"  -X PUT http://localhost:8080/counter/decrement    

## delete an existing counter

send DELETE request to /counter/NAME_OF_COUNTER
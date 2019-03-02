package server.counter.mongodb;

import com.mongodb.*;

import org.springframework.stereotype.Component;
import server.counter.Counter;
import server.errorHandling.exceptions.CounterAlreadyExistsException;
import server.errorHandling.exceptions.NoSuchCounterException;

import java.net.UnknownHostException;
import java.util.*;

@Component
public class MongoCounterDispenser {

    MongoClient mongoClient;
    DB database;
    DBCollection counters;

    public MongoCounterDispenser() {
        try {
            this.mongoClient = new MongoClient();
            // this will close the client when the application terminates
            Runtime.getRuntime().addShutdownHook(new Thread(new ClientShutdown(mongoClient)));
            database = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
            counters = database.getCollection(DatabaseConstants.COUNTER_COLLECTION);

        } catch (UnknownHostException e) {
            System.out.println("wrong database host adress");
        }
    }

    public Optional<Counter> getCounter(String name) {
        DBCursor cursor = queryFor(name);
        if (!cursor.hasNext()) {
            return null;
        }
        DBObject counter = cursor.one();
        return Optional.ofNullable(convertToCounter(counter));
    }

    public void saveCounter(Counter counter) {
        counters.insert(convertToDBObject(counter));
    }

    public void addCounter(Counter counter) throws CounterAlreadyExistsException{
        if(exists(counter.getName())) {
            throw new CounterAlreadyExistsException(counter.getName());
        }
        saveCounter(counter);
    }

    private Counter convertToCounter(DBObject data) {
        String name = (String) data.get("name");
        long value = (long) data.get("value");
        return new Counter(value, name);
    }

    private DBObject convertToDBObject(Counter counter) {
        return new BasicDBObject("name",counter.getName())
                .append("value",counter.get());
    }

    private DBCursor queryFor(String name) {
        DBObject query = new BasicDBObject("name", name);
        return counters.find(query);
    }

    public List<Counter> findAll() {
        DBCursor cursor = counters.find();
        List<Counter> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Counter counter = convertToCounter(cursor.next());
            list.add(counter);
        }
        return list;
    }

    public void update (Counter counter) throws NoSuchCounterException {
        if (!exists(counter.getName())) {
            throw new NoSuchCounterException(counter.getName());
        }
        DBObject query = new BasicDBObject("name", counter.getName());
        counters.update(query, convertToDBObject(counter));
    }

    public boolean exists(String name) {
        return queryFor(name).hasNext();
    }

    public void delete(String name) {
        counters.remove(new BasicDBObject("name", name));
    }

}

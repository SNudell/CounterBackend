package server.counter.mongodb;

import com.mongodb.MongoClient;

public class ClientShutdown implements Runnable {

    MongoClient client;

    public ClientShutdown (MongoClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.close();
    }
}

package server.counter.mongodb;

import java.io.IOException;

public class MongodShutdown implements Runnable {
    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("mongod --shutdown");
            System.out.println("shutdown mongod");
        } catch (IOException e) {
            System.out.println("Unable to properly shutdown the mongod");
        }
    }
}

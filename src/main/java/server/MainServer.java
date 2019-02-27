package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.counter.Counter;
import server.counter.mongodb.MongodShutdown;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class MainServer {

    public static void main(String args[]) {
        startMongoDB();
        SpringApplication.run(MainServer.class, args);
    }


    public static void startMongoDB() {
        new File("./data/db").mkdirs();
        new File("./logs").mkdirs();
        try {
            Runtime.getRuntime().exec("mongod --fork --dbpath data/db --logpath ./logs/mongodb.log");
            System.out.println("startet mongod");
        } catch (IOException e) {
            System.out.println("unable to start mongod");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new MongodShutdown()));
    }
}

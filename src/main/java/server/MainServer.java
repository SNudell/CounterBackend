package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.counter.Counter;

@SpringBootApplication
public class MainServer {

    private Counter counter = new Counter(0);

    public static void main(String args[]) {
        SpringApplication.run(MainServer.class, args);
    }

}

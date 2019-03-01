package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import server.counter.mongodb.MongodShutdown;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class MainServer {

    public static void main(String args[]) {
        startMongoDB();
        String hostAdress = getIPAddress();
        SpringApplication app = new SpringApplication(MainServer.class);
        Map<String,Object> properties = new HashMap<>();
        properties.put("server.address", hostAdress);
        properties.put("server.ssl.key-store", "classpath:keystore.countmeup");
        properties.put("server.ssl.key-store-password", "sdgoD923sdingwe");
        properties.put("server.ssl.keyStoreType", "PKCS12");
        properties.put("server.ssl.keyAlias", "tomcat");
        app.setDefaultProperties(properties);
        app.run(args);
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

    public static String getIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            System.out.println("IP Address:- " + inetAddress.getHostAddress());
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e ) {
            System.out.println("could not get host address will start on localhost instead");
            return "127.0.0.1";
        }
    }
}

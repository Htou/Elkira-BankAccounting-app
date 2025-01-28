package org.example;

import static spark.Spark.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.example.config.AppModule;
import org.example.service.BankingApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        setupCORS();
        setupRoutes();

        // Startup logic
        logger.info("Application starting...");

        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Application shutting down...");
            stop(); // Stop Spark server
        }));

        BankingApplication app = new BankingApplication();
        app.run();
    }

    private static void setupCORS() {
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
            response.type("application/json");
        });
    }

    private static void setupRoutes() {
        // Example route
        get("/hello", (req, res) -> "Hello and welcome!");
    }
}

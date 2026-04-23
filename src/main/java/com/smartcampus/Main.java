package com.smartcampus;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import com.smartcampus.config.ApiApplication;

public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";
    public static final String API_BASE_PATH = "api/v1/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Create a resource config that scans for JAX-RS resources and providers
        final ApiApplication rc = new ApiApplication();

        // Create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI + API_BASE_PATH), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with endpoints available at "
                + "%s\nHit Ctrl-C to stop it...", BASE_URI + API_BASE_PATH));
        
        // Wait for user input to stop the server
        System.in.read();
        server.shutdownNow();
    }
}

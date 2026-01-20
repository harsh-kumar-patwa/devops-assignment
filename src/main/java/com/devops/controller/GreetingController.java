package com.devops.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for greeting endpoints.
 * Demonstrates basic REST API functionality.
 */
@RestController
@RequestMapping("/api")
public class GreetingController {

    /** Default name for greeting. */
    private static final String DEFAULT_NAME = "World";

    /**
     * Health check endpoint.
     * Returns the current health status of the application.
     *
     * @return health status response
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is running");
        return ResponseEntity.ok(response);
    }

    /**
     * Greeting endpoint.
     * Returns a personalized greeting message.
     *
     * @param name the name to greet (optional, defaults to "World")
     * @return greeting response
     */
    @GetMapping("/greeting")
    public ResponseEntity<Map<String, String>> greeting(
            @RequestParam(value = "name", defaultValue = DEFAULT_NAME) 
            final String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("timestamp", java.time.Instant.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Version endpoint.
     * Returns the application version information.
     *
     * @return version information
     */
    @GetMapping("/version")
    public ResponseEntity<Map<String, String>> version() {
        Map<String, String> response = new HashMap<>();
        response.put("version", "1.0.0");
        response.put("name", "DevOps Demo Application");
        return ResponseEntity.ok(response);
    }
}

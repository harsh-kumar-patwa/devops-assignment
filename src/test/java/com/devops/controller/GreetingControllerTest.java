package com.devops.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for GreetingController.
 * Tests all REST endpoints for correct behavior.
 */
@WebMvcTest(GreetingController.class)
class GreetingControllerTest {

    /** MockMvc for testing REST endpoints. */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test health endpoint returns UP status.
     */
    @Test
    void healthEndpointShouldReturnUp() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.message").value("Application is running"));
    }

    /**
     * Test greeting endpoint with default name.
     */
    @Test
    void greetingEndpointShouldReturnDefaultGreeting() throws Exception {
        mockMvc.perform(get("/api/greeting"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, World!"));
    }

    /**
     * Test greeting endpoint with custom name.
     */
    @Test
    void greetingEndpointShouldReturnCustomGreeting() throws Exception {
        mockMvc.perform(get("/api/greeting").param("name", "DevOps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, DevOps!"));
    }

    /**
     * Test version endpoint returns correct version.
     */
    @Test
    void versionEndpointShouldReturnVersion() throws Exception {
        mockMvc.perform(get("/api/version"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.name").value("DevOps Demo Application"));
    }
}

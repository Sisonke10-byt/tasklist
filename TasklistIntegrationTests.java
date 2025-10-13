package com.example.tasklist;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

@SpringBootTest
@ActiveProfiles("ci") // Postgres in CI Docker container
class TasklistIntegrationTests {

    @Test
    void contextLoads() {
        // Verifies Spring context loads with real Postgres
    }
}


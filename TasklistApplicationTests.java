package com.example.tasklist;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

@SpringBootTest
@ActiveProfiles("test")
class TasklistApplicationTests {

    @Test
    void contextLoads() {
        // This test simply verifies that the Spring context loads successfully
    }
}


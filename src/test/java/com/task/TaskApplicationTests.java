package com.task;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TaskApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        assertNotNull(applicationContext);
    }
}

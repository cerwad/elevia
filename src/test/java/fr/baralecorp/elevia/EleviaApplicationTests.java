package fr.baralecorp.elevia;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("dev")
class EleviaApplicationTests {
    @Value("${env}")
    private String environment;

    @Test
    void contextLoads() {
        assertEquals("dev", environment);
    }

}

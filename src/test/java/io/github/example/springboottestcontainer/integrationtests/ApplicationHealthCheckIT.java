package io.github.example.springboottestcontainer.integrationtests;

import com.redis.testcontainers.RedisContainer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Disabled
class ApplicationHealthCheckIT {

    @Autowired
    MockMvc mockMvc;

    @Container
    static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest"))
            .withReuse(true)
            .withCommand("redis-server --requirepass P@ssword");

    @DynamicPropertySource
    static void configureRedisProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getHost);
        registry.add("spring.data.redis.port", redisContainer::getFirstMappedPort);
        registry.add("spring.data.redis.password", () -> "P@ssword");
    }

    @Test
    @SneakyThrows
    void should_ReturnUP_When_ApplicationIsUpAndDependenciesAreUp() {

        var result = mockMvc.perform(get("/actuator/health")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.redis.status").value("UP"));
    }
}
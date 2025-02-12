package io.github.example.springboottestcontainer.base;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

public class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest"))
            .withReuse(true)
            .withCommand("redis-server --requirepass P@ssword");

    static {
        Startables.deepStart(redisContainer).join();
    }

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(
                "spring.data.redis.host=" + redisContainer.getHost(),
                "spring.data.redis.port=" + redisContainer.getFirstMappedPort(),
                "spring.data.redis.password=" + "P@ssword"
        ).applyTo(ctx.getEnvironment());
    }
}
package io.github.example.springboottestcontainer.integrationtests;

import io.github.example.springboottestcontainer.base.IntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class GetTestRestEndpointV2IT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void should_ReturnUP_When_ApplicationIsUpAndDependenciesAreUp() {

        var result = mockMvc.perform(get("/v1/tests/test")
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("test"));
    }
}
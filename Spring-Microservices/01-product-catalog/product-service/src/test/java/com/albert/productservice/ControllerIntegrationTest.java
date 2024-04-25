package com.albert.productservice;

import com.albert.productservice.dto.ProductRequest;
import com.albert.productservice.model.Product;
import com.albert.productservice.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.albert.productservice.util.ProductUtil.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@Testcontainers // https://java.testcontainers.org/
@AutoConfigureMockMvc
public class ControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository repository;

    @Container
    private static MongoDBContainer mongoDBContainer =
            new MongoDBContainer("mongo:5.0.25");

    @DynamicPropertySource
    private static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",
                mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    public void save_ReturnsProductResponse_WhenSuccessful() throws Exception {
        final ProductRequest productRequest = getProductRequest();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/app/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.name")
                        .value(productRequest.getName())
                );
    }

    @Test
    public void findAll_ReturnsListOfProductResponse_WhenSuccessful() throws Exception {
        final Product productToSave = getProductToSave();
        repository.save(productToSave);

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/app/product")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$[0].name")
                                .value(productToSave.getName())
                )
                .andReturn();
    }
}


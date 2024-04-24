package org.albert.testsunitintregration.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAll_ReturnsAllProducts_WhenSuccessful() throws Exception {
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.name == 'Crucible')]").exists())
                .andExpect(jsonPath("$..[?(@.price == '30.3456')]").exists());
//      Explanation of the JsonPath expression:
//      - $..: This selects all elements within the JSON response, including nested objects and arrays.
//      - []: This is a filter expression.
//      - ?(@.name == 'Crucible'): This checks if the name attribute of the current element (@) is equal to "Crucible".
//      - .exists(): This asserts that at least one element exists in the filtered results.
    }
}
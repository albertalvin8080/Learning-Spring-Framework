package org.albert.integrationtestscustommockuser.controllers;

import org.albert.integrationtestscustommockuser.security.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
//    @WithMockUser
    @WithCustomMockUser
    void data_ReturnsStringData_WhenSuccessful() throws Exception {
        mockMvc
                .perform(get("/demo/data"))
                .andExpectAll(
                        status().isOk(),
                        content().string("Data: 01000001")
                );
    }

    @Test
    @WithCustomMockUser(priority = "HIGH")
    void dataPriority_ReturnsStringData_WhenSuccessful() throws Exception {
        mockMvc
                .perform(get("/demo/dataPriority"))
                .andExpectAll(
                        status().isOk(),
                        content().string("Data from dataPriority.")
                );
    }

    @Test
    @WithCustomMockUser(priority = "LOW")
    void dataPriority_ReturnsHttpStatusFORBIDDEN_WhenPriorityIsWrong() throws Exception {
        mockMvc
                .perform(get("/demo/dataPriority"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService")
    void data_ReturnsStringData_WhenSuccessful_WithUserDetails() throws Exception {
        mockMvc
                .perform(get("/demo/data"))
                .andExpectAll(
                        status().isOk(),
                        content().string("Data: 01000001")
                );
    }
}
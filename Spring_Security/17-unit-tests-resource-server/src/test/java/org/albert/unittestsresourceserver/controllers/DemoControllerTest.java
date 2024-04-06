package org.albert.unittestsresourceserver.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void data_ReturnsStringData_WhenSuccessful() throws Exception {
        mockMvc
                .perform(get("/demo/data")
                        .with(jwt())
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("Data: 01000001")
                );
    }

    @Test
    void save_ReturnsHttpStatusCREATED_WhenSuccessful() throws Exception {
        mockMvc
                .perform(post("/demo/save")
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("write"))
                        )
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("Data: 01000001")
                )
                .andExpect(status().isCreated());
    }

    /*
    * The jwt() method and @WithMockUser annotation work differently in terms of
    * how they set up the security context for the test.
    *
    * The jwt() method creates a JwtAuthenticationToken which is populated with
    * the claims from the provided JWT. This includes the authorities which are
    * typically sourced from the scp or scope claim or can be mapped from other
    * claims using JwtAuthenticationConverter.
    *
    * On the other hand, @WithMockUser sets up the security context with a
    * UsernamePasswordAuthenticationToken which doesn't involve JWT claims.
    * The authorities are directly provided as an attribute.
    * */
//    @Test
//    @WithMockUser(authorities = {"write"}) // doesn't work with JWT
//    void save_ReturnsHttpStatusCREATED_WhenSuccessful_WithMockUser() throws Exception {
//        mockMvc
//                .perform(post("/demo/save")
//                        .contentType(MediaType.TEXT_PLAIN)
//                        .content("Data: 01000001")
//                )
//                .andExpect(status().isCreated());
//    }
}
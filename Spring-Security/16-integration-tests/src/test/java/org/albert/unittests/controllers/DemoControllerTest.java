package org.albert.unittests.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void data_ReturnsStringData_WhenSuccessful() throws Exception {
        mockMvc
                .perform(get("/demo/data")
                        .with(httpBasic("albert", "1234"))
                )
                .andExpectAll(
                        status().isOk(),
                        content().string("This is the data.")
                );
    }

    @Test
    void saveData_ReturnsHttpStatusCREATED_WhenSuccessful() throws Exception {
        mockMvc
                .perform(post("/demo/save")
                        .with(httpBasic("albert", "1234"))
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("data to be saved.")
                )
                .andExpect(status().isCreated());
    }

    @Test
    void updateData_ReturnsHttpStatusNOCONTENT_WhenSuccessful() throws Exception {
        mockMvc
                .perform(put("/demo/update")
                        .with(httpBasic("albert", "1234"))
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("data to be updated.")
                )
                .andExpect(status().isNoContent());
    }

    @Test
    // @WithMockUser creates a MockSecurityContext for testing (with a default MockUser) which makes the MockMvc to skip the authentication and focus only in the authorization.
    @WithMockUser
    void updateData_ReturnsHttpStatusFORBIDDEN_WhenUserDoesntHaveRightAuthority_WithMockUser() throws Exception {
        mockMvc
                .perform(put("/demo/update")
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("data to be updated.")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "write")
    void updateData_ReturnsHttpStatusNOCONTENT_WhenUserSuccessful_WithMockUser() throws Exception {
        mockMvc
                .perform(put("/demo/update")
                        .with(csrf())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("data to be updated.")
                )
                .andExpect(status().isNoContent());
    }

}
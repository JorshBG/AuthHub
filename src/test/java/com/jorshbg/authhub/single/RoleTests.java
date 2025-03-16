package com.jorshbg.authhub.single;

import com.jorshbg.authhub.system.security.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.mockito.ArgumentMatchers.contains;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Profile("test")
public class RoleTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    private String token;

    @BeforeEach
    public void setup() {
        this.token = this.jwtProvider.getAccessToken("admin", new LinkedHashMap<>(Map.of(
                "Authorities", "ADMIN")));
    }

    @Test
    void testGetAllRoles() throws Exception {
        mockMvc.perform(get("/api/v1/roles").header("Authorization", "Bearer " + this.token))
                .andExpect(status().isOk());
    }

    @Test
    void testStore() throws Exception {
        this.mockMvc.perform(post("/api/v1/roles").content("{\"name\": \"ADMIN\"}").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + this.token))
                .andExpect(status().isCreated())
                .andExpect(content().json(contains("{\"name\": \"ADMIN\"}")));
    }
}

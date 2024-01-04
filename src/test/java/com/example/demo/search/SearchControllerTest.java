package com.example.demo.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetMovie_HappyPath_ResultInMovieBeingReturned() throws Exception {

        String title = "1917";
        String director = "Sam Mendes";
        mockMvc.perform(get("/search/1917"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.director").value(director));
    }

    @Test
    void testGetMovie_MovieNotFound_ResultInMovieNotFoundMsg() throws Exception {

        String errorMsg = "Movie not found!";

        mockMvc.perform(get("/search/19171"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMsg));
    }
}
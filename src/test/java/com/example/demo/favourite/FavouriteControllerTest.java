package com.example.demo.favourite;

import com.example.demo.favourite.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FavouriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void testAddMovie_HappyPath_ResultInMovieBeingSaved() throws Exception {

        Movie movie = Movie.builder()
                .title("Blade Runner")
                .plot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .genre("Action, Drama, Sci-Fi")
                .director("Ridley Scott")
                .poster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(movie.getTitle()))
                .andExpect(jsonPath("$.plot").value(movie.getPlot()))
                .andExpect(jsonPath("$.genre").value(movie.getGenre()))
                .andExpect(jsonPath("$.director").value(movie.getDirector()))
                .andExpect(jsonPath("$.poster").value(movie.getPoster()));
    }

    @Test
    void testAddMovie_MovieNotSave_ResultInMovieBeingNotSaved() throws Exception {

        Movie movie = new Movie();

        String errorMsg = "Validation errors";

        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    @Test
    void testGetMovie_HappyPath_ResultInMovieBeingReturned() throws Exception {

        Movie movie = Movie.builder()
                .title("Blade Runner")
                .plot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .genre("Action, Drama, Sci-Fi")
                .director("Ridley Scott")
                .poster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg")
                .build();

        int id = favoriteRepository.save(movie).getId();

        mockMvc.perform(get("/api/v1/favorites/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(movie.getTitle()))
                .andExpect(jsonPath("$.plot").value(movie.getPlot()))
                .andExpect(jsonPath("$.genre").value(movie.getGenre()))
                .andExpect(jsonPath("$.director").value(movie.getDirector()))
                .andExpect(jsonPath("$.poster").value(movie.getPoster()))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testGetMovie_MovieNotFound_ResultInMovieNotFound() throws Exception {

        int id = 123;
        String errorMsg = "The movie was not found";

        mockMvc.perform(get("/api/v1/favorites/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(errorMsg));
    }

    @Test
    void testGetAllMovies_HappyPath_ResultInMoviesBeringReturned() throws Exception {

        Movie movie = Movie.builder()
                .title("Blade Runner")
                .plot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .genre("Action, Drama, Sci-Fi")
                .director("Ridley Scott")
                .poster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg")
                .build();

        int id = favoriteRepository.save(movie).getId();

        mockMvc.perform(get("/api/v1/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value(movie.getTitle()))
                .andExpect(jsonPath("$[0].plot").value(movie.getPlot()))
                .andExpect(jsonPath("$[0].genre").value(movie.getGenre()))
                .andExpect(jsonPath("$[0].director").value(movie.getDirector()))
                .andExpect(jsonPath("$[0].poster").value(movie.getPoster()))
                .andExpect(jsonPath("$[0].id").value(id));
    }

    @Test
    void testFindAll_MoviesNotFound_ResultInEmptyListBeingReturned() throws Exception {

        mockMvc.perform(get("/api/v1/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
package com.example.demo.search;

import com.example.demo.common.MovieNotFoundException;
import com.example.demo.search.model.MovieDto;
import com.example.demo.search.webclient.SearchClient;
import com.example.demo.search.webclient.model.OpenMovieDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private SearchClient searchClient;

    @Test
    void TestGetMovie_HappyPath_ResultsInMovieBeingReturned() {

        String title = "Blade Runner";

        OpenMovieDto dataFromApi = OpenMovieDto.builder()
                .Title("Blade Runner")
                .Plot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .Genre("Action, Drama, Sci-Fi")
                .Director("Ridley Scott")
                .Poster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg")
                .build();
        when(searchClient.getMovie(title)).thenReturn(dataFromApi);

        MovieDto returned = searchService.getMovie(title);

        verify(searchClient).getMovie(title);
        assertEquals(dataFromApi.getTitle(), returned.getTitle());
        assertEquals(dataFromApi.getPlot(), returned.getPlot());
        assertEquals(dataFromApi.getGenre(), returned.getGenre());
        assertEquals(dataFromApi.getDirector(), returned.getDirector());
        assertEquals(dataFromApi.getPoster(), returned.getPoster());
    }

    @Test
    void TestGetMovie_MovieNotFound_ResultsInMovieNotFoundException() {

        String title = "Blade Runnerr";
        String exceptionMsg = "Movie not found!";
        OpenMovieDto dataFromApi = OpenMovieDto.builder()
                .Error(exceptionMsg)
                .Response("False")
                .build();

        when(searchClient.getMovie(title)).thenReturn(dataFromApi);

        assertThatExceptionOfType(MovieNotFoundException.class)
                .isThrownBy(() -> searchService.getMovie(title))
                .withMessage(exceptionMsg);

        verify(searchClient).getMovie(title);
    }
}
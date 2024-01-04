package com.example.demo.favourite;

import com.example.demo.common.MovieNotFoundException;
import com.example.demo.favourite.model.Movie;
import com.example.demo.favourite.model.command.CreateMovieCommand;
import com.example.demo.favourite.model.dto.MovieFavouriteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavouriteServiceTest {

    @InjectMocks
    private FavouriteService favouriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Captor
    private ArgumentCaptor<Movie> movieArgumentCaptor;

    @Test
    void testCreate_ResultInMovieBeingSaved() {

        int expectedMovieId = 1;
        CreateMovieCommand command = new CreateMovieCommand()
                .setTitle("Blade Runner")
                .setPlot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .setGenre("Action, Drama, Sci-Fi")
                .setDirector("Ridley Scott")
                .setPoster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg");

        when(favoriteRepository.save(any(Movie.class))).thenAnswer(args -> {
            Movie movie = (Movie) args.getArguments()[0];
            movie.setId(expectedMovieId);
            return movie;
        });

        MovieFavouriteDto returned = favouriteService.create(command);

        verify(favoriteRepository).save(movieArgumentCaptor.capture());
        Movie saved = movieArgumentCaptor.getValue();
        assertEquals(command.getTitle(), saved.getTitle());
        assertEquals(command.getPlot(), saved.getPlot());
        assertEquals(command.getGenre(), saved.getGenre());
        assertEquals(command.getDirector(), saved.getDirector());
        assertEquals(command.getPoster(), saved.getPoster());

        assertEquals(expectedMovieId, returned.getId());
        assertEquals(command.getTitle(), returned.getTitle());
        assertEquals(command.getPlot(), returned.getPlot());
        assertEquals(command.getGenre(), returned.getGenre());
        assertEquals(command.getDirector(), returned.getDirector());
        assertEquals(command.getPoster(), returned.getPoster());
    }

    @Test
    void testGetById_HappyPath_ResultsInMovieBeingReturned() {

        int movieId = 1;
        Movie movieFromRepo = Movie.builder()
                .id(movieId)
                .title("Blade Runner")
                .plot("A blade runner must pursue and terminate four replicants who stole a ship in space and have returned to Earth to find their creator.")
                .genre("Action, Drama, Sci-Fi")
                .director("Ridley Scott")
                .poster("https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg")
                .build();

        when(favoriteRepository.findById(movieId)).thenReturn(Optional.of(movieFromRepo));

        MovieFavouriteDto returned = favouriteService.getById(movieId);

        assertEquals(movieFromRepo.getId(), returned.getId());
        assertEquals(movieFromRepo.getTitle(), returned.getTitle());
        assertEquals(movieFromRepo.getPlot(), returned.getPlot());
        assertEquals(movieFromRepo.getGenre(), returned.getGenre());
        assertEquals(movieFromRepo.getDirector(), returned.getDirector());
        assertEquals(movieFromRepo.getPoster(), returned.getPoster());
        verify(favoriteRepository).findById(movieId);
    }

    @Test
    void testGetById_MovieNotFound_ResultsInMovieNotFoundException() {

        String exceptionMsg = "The movie was not found";
        int movieId = 1;

        when(favoriteRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(MovieNotFoundException.class)
                .isThrownBy(() -> favouriteService.getById(movieId))
                .withMessage(exceptionMsg);

        verify(favoriteRepository).findById(movieId);
    }

    @Test
    void testGetAllMovies_ResultInMovieListBeingReturned() {

        Movie movie1 = new Movie();
        Movie movie2 = new Movie();
        List<Movie> movieList = List.of(movie1, movie2);

        when(favoriteRepository.findAll()).thenReturn(movieList);

        List<MovieFavouriteDto> returned = favouriteService.getAllMovies();

        assertEquals(movieList.size(), returned.size());
        verify(favoriteRepository).findAll();
    }
}
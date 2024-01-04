package com.example.demo.favourite;

import com.example.demo.common.MovieNotFoundException;
import com.example.demo.favourite.model.Movie;
import com.example.demo.favourite.model.command.CreateMovieCommand;
import com.example.demo.favourite.model.dto.MovieFavouriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteService {

    private final FavoriteRepository movieRepository;

    public MovieFavouriteDto create(CreateMovieCommand command) {
        Movie toSave = command.toEntity();
        return MovieFavouriteDto.fromEntity(movieRepository.save(toSave));
    }

    public MovieFavouriteDto getById(int id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("The movie was not found"));
        return MovieFavouriteDto.fromEntity(movie);
    }

    public List<MovieFavouriteDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(MovieFavouriteDto::fromEntity)
                .toList();
    }
}

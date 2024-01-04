package com.example.demo.favourite;

import com.example.demo.favourite.model.command.CreateMovieCommand;
import com.example.demo.favourite.model.dto.MovieFavouriteDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/favorites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieFavouriteDto addMovie(@RequestBody @Valid CreateMovieCommand command) {
        return favouriteService.create(command);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MovieFavouriteDto getMovie(@PathVariable int id) {
        return favouriteService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieFavouriteDto> getAllMovies() {
        return favouriteService.getAllMovies();
    }

}

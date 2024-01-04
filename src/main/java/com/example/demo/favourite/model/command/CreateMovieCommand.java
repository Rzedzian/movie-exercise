package com.example.demo.favourite.model.command;

import com.example.demo.favourite.model.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateMovieCommand {

    @NotBlank
    @NotNull
    private String title;

    @NotNull
    @NotBlank
    private String plot;

    @NotNull
    @NotBlank
    private String genre;

    @NotNull
    @NotBlank
    private String director;

    @NotNull
    @NotBlank
    private String poster;

    public Movie toEntity() {
        return Movie.builder()
                .title(title)
                .plot(plot)
                .genre(genre)
                .director(director)
                .poster(poster)
                .build();
    }
}

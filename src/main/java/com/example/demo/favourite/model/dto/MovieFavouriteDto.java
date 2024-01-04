package com.example.demo.favourite.model.dto;

import com.example.demo.favourite.model.Movie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieFavouriteDto {

    private int id;
    private String title;
    private String plot;
    private String genre;
    private String director;
    private String poster;

    public static MovieFavouriteDto fromEntity(Movie movie) {
        return MovieFavouriteDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .plot(movie.getPlot())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .poster(movie.getPoster())
                .build();
    }
}

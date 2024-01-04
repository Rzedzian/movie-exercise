package com.example.demo.search.model;

import com.example.demo.search.webclient.model.OpenMovieDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieDto {

    private String title;
    private String plot;
    private String genre;
    private String director;
    private String poster;

    public static MovieDto fromAPI(OpenMovieDto openMovieDto) {
        return MovieDto.builder()
                .title(openMovieDto.getTitle())
                .plot(openMovieDto.getPlot())
                .genre(openMovieDto.getGenre())
                .director(openMovieDto.getDirector())
                .poster(openMovieDto.getPoster())
                .build();
    }
}

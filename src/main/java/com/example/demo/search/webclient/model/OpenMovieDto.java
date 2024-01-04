package com.example.demo.search.webclient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenMovieDto {

    private String Title;
    private String Plot;
    private String Genre;
    private String Director;
    private String Poster;
    private String Error;
    private String Response;
}

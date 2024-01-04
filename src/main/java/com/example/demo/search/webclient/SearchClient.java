package com.example.demo.search.webclient;

import com.example.demo.search.webclient.model.OpenMovieDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SearchClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String MOVIE_URL = "https://www.omdbapi.com/";
    private static final String API_KEY = "bf46dacd";

    public OpenMovieDto getMovie(String title) {

        return restTemplate.getForObject(MOVIE_URL + "?apikey={apiKey}&t={title}", OpenMovieDto.class, API_KEY, title);
    }
}

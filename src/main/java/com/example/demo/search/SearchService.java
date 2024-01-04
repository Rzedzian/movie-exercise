package com.example.demo.search;

import com.example.demo.common.MovieNotFoundException;
import com.example.demo.search.model.MovieDto;
import com.example.demo.search.webclient.SearchClient;
import com.example.demo.search.webclient.model.OpenMovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchClient client;

    public MovieDto getMovie(String title) {
        OpenMovieDto data = client.getMovie(title);
        if (data.getError() != null && data.getResponse() != null) {
            throw new MovieNotFoundException(data.getError());
        }
        return MovieDto.fromAPI(data);
    }
}

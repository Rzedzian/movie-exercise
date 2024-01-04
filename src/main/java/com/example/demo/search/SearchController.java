package com.example.demo.search;

import com.example.demo.search.model.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search/{titleMovie}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDto getMovie(@PathVariable String titleMovie) {
        return searchService.getMovie(titleMovie);
    }
}

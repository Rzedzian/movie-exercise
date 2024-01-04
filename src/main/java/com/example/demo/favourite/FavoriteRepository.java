package com.example.demo.favourite;

import com.example.demo.favourite.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Movie, Integer> {

}

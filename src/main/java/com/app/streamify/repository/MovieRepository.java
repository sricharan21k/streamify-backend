package com.app.streamify.repository;

import com.app.streamify.model.Album;
import com.app.streamify.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("from Movie m where LOWER(m.title) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.genre) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.director) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.producer) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.cast) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.otherGenres) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.franchiseName) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.production) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.distributor) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.language) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Movie> searchLibrary(String keyword);


    @Query("from Movie m where LOWER(m.genre) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(m.otherGenres) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Movie> getMoviesByGenre(String keyword);
}

package com.app.streamify.repository;

import com.app.streamify.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("from Show s where LOWER(s.title) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.genre) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.otherGenres) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.creator) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.starring) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.composer) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Show> searchLibrary(String keyword);

    @Query("from Show s where LOWER(s.genre) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.otherGenres) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Show> getShowsByGenre(String keyword);
}

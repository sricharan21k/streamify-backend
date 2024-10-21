package com.app.streamify.repository;

import com.app.streamify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("from Song s where LOWER(s.title) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.genre) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.artist) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(s.featuring) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Song> searchLibrary(String keyword);
}

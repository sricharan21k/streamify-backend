package com.app.streamify.repository;

import com.app.streamify.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);

    List<Album> findByGenre(String genre);

    @Query("from Album a where LOWER(a.title) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(a.genre) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Album> searchLibrary(String keyword);
}

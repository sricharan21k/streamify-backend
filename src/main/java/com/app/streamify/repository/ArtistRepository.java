package com.app.streamify.repository;

import com.app.streamify.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(
            "from Artist a where LOWER(a.name) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Artist> searchLibrary(String keyword);
}

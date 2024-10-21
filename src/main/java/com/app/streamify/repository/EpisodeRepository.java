package com.app.streamify.repository;

import com.app.streamify.model.Album;
import com.app.streamify.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

    @Query("from Episode e where LOWER(e.title) like LOWER(CONCAT('%', :keyword, '%'))")
    List<Episode> searchLibrary(String keyword);
}

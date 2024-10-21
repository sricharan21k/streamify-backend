package com.app.streamify.repository;

import com.app.streamify.model.Album;
import com.app.streamify.model.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

    List<Podcast> findByTopicContaining(String topic);


    @Query("from Podcast p where LOWER(p.title) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(p.topic) like LOWER(CONCAT('%', :keyword, '%'))" +
            " or LOWER(p.host) like LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<Podcast> searchLibrary(String keyword);
}

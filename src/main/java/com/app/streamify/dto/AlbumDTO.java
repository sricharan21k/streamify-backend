package com.app.streamify.dto;

import com.app.streamify.model.Artist;
import com.app.streamify.model.Song;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer year;
    private boolean isSingle;
    private String albumCover;
    private String artistName;
    private String artistImage;
    private List<Long> tracks;
}

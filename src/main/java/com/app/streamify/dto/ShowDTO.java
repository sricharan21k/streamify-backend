package com.app.streamify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer seasons;
    private String creator;
    private String starring;
    private String composer;
    private Integer initialRelease;
    private Double rating;
    private String language;
    private String poster;
    private String description;
    private String otherGenres;
    private List<Long> episodeList;
}

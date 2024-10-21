package com.app.streamify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDTO {
    private Long id;
    private Integer episode;
    private String title;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer season;
    private String description;
}

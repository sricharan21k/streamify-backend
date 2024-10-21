package com.app.streamify.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tv_show")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "showName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Episode> episodeList = new ArrayList<>();
}

package com.app.streamify.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    private String director;
    private String producer;
    @Column(name = "movie_cast")
    private String cast;
    private String crew;
    private Integer year;
    private String poster;
    private Integer runtime;
    private String production;
    private String distributor;
    private String language;
    private Double rating;
    private boolean isFranchise;
    private String franchiseName;
    private String landscapePoster;
    private String description;
    private String otherGenres;
}

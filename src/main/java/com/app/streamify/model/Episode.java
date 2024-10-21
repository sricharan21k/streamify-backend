package com.app.streamify.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer episode;
    private String title;
    private LocalDate releaseDate;
    private Integer duration;
    private Integer season;
    private String description;

    @ManyToOne
    @JsonIgnore
    private Show showName;


}

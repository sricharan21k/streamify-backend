package com.app.streamify.controller;

import com.app.streamify.dto.EpisodeDTO;
import com.app.streamify.dto.ShowDTO;
import com.app.streamify.model.Movie;
import com.app.streamify.model.Show;
import com.app.streamify.repository.ShowRepository;
import com.app.streamify.service.ShowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/{showId}")
    public ShowDTO getShow(@PathVariable Long showId){
        return showService.getShow(showId);
    }

    @GetMapping("episode/{episodeId}")
    public EpisodeDTO getEpisode(@PathVariable Long episodeId){
        return showService.getEpisode(episodeId);
    }

    @GetMapping("/genre/{genre}")
    public List<Show> getShowsByGenre(@PathVariable String genre){
        return showService.getShowsByGenre(genre);
    }

    @GetMapping("/new-releases")
    public List<Show> getNewReleases() {
        return showService.getNewReleases();
    }

    @GetMapping("/genres")
    public Map<String, List<Show>> getShowGenres(){
        return showService.getShowGenres();
    }
}

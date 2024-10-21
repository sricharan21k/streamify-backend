package com.app.streamify.controller;

import com.app.streamify.model.Podcast;
import com.app.streamify.service.PodcastService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/podcasts")
public class PodcastController {
    private final PodcastService podcastService;

    public PodcastController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @GetMapping("/new-releases")
    public List<Podcast> getNewPodcasts(){
        return podcastService.getPopularPodcasts();
    }

    @GetMapping("/trending")
    public List<Podcast> getTrendingPodcasts(){
        return podcastService.getPopularPodcasts();
    }

    @GetMapping("/popular")
    public List<Podcast> getPopularPodcasts(){
        return podcastService.getPopularPodcasts();
    }

    @GetMapping("/topic/{topic}")
    public List<Podcast> getPopularPodcasts(@PathVariable String topic){
        return podcastService.getPodcastsOfTopic(topic);
    }

    @GetMapping("/all-topics")
    public List<String> getAllTopics(){
        return podcastService.getAllTopics();
    }
}

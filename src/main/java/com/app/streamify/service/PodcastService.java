package com.app.streamify.service;

import com.app.streamify.model.Podcast;
import com.app.streamify.repository.PodcastRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PodcastService {
    private final PodcastRepository podcastRepository;

    public PodcastService(PodcastRepository podcastRepository) {
        this.podcastRepository = podcastRepository;
    }

    public List<Podcast> getPopularPodcasts(){
        return podcastRepository.findAll().stream().limit(10).toList();
    }

    public List<Podcast> getPodcastsOfTopic(String topic){
        return podcastRepository.findByTopicContaining(topic);
    }

    public List<String> getAllTopics(){
        List<String> topics = podcastRepository.findAll().stream().map(Podcast::getTopic).toList();
        List<String> topicList = new ArrayList<>();
        topics.forEach(topic -> {
            List<String> list = Arrays.stream(topic.split(",")).toList();
            topicList.addAll(list);
        });
        return topicList.stream().map(String::toLowerCase).distinct().sorted().toList();
    }
}

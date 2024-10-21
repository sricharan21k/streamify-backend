package com.app.streamify.service;

import com.app.streamify.dto.EpisodeDTO;
import com.app.streamify.dto.ShowDTO;
import com.app.streamify.model.Episode;
import com.app.streamify.model.Movie;
import com.app.streamify.model.Show;
import com.app.streamify.repository.EpisodeRepository;
import com.app.streamify.repository.ShowRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;
    private final ModelMapper modelMapper;

    public ShowService(ShowRepository showRepository, EpisodeRepository episodeRepository, ModelMapper modelMapper) {
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
        this.modelMapper = modelMapper;
    }

    public ShowDTO getShow(Long showId){
        Show show = showRepository.findById(showId).orElseThrow();
        return showMapper(show);
    }

    public EpisodeDTO getEpisode(Long episodeId){
        Episode episode = episodeRepository.findById(episodeId).orElseThrow();
        return modelMapper.map(episode, EpisodeDTO.class);
    }

    public List<Show> getNewReleases(){
        return showRepository.findAll();
    }

    public List<Show> getShowsByGenre(String genre){
        return showRepository.getShowsByGenre(genre);
    }

    public Map<String, List<Show>> getShowGenres(){
        return showRepository.findAll().stream().sorted(Comparator.comparing(Show::getGenre))
                .collect(Collectors.groupingBy(Show::getGenre));
    }

    private ShowDTO showMapper(Show show){

        List<Long> episodeList = show.getEpisodeList().stream().map(Episode::getId).toList();
        return ShowDTO.builder().id(show.getId()).title(show.getTitle()).genre(show.getGenre())
                .creator(show.getCreator()).composer(show.getComposer()).poster(show.getPoster())
                .language(show.getLanguage()).rating(show.getRating()).initialRelease(show.getInitialRelease())
                .starring(show.getStarring()).otherGenres(show.getOtherGenres()).episodeList(episodeList)
                .description(show.getDescription()).seasons(show.getSeasons()).build();
    }
}

package com.app.streamify.service;

import com.app.streamify.dto.MediaDTO;
import com.app.streamify.model.*;
import com.app.streamify.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final PodcastRepository podcastRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;

    public AppService(ArtistRepository artistRepository, SongRepository songRepository,
                      AlbumRepository albumRepository,
                      PodcastRepository podcastRepository,
                      MovieRepository movieRepository,
                      ShowRepository showRepository, EpisodeRepository episodeRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.podcastRepository = podcastRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
    }

    public List<MediaDTO> searchLibrary(String keyword){
        List<MediaDTO> searchResults = new ArrayList<>();

        List<Artist> artists = artistRepository.searchLibrary(keyword);
        List<Song> songs = songRepository.searchLibrary(keyword);
        List<Album> albums = albumRepository.searchLibrary(keyword);
        List<Podcast> podcasts = podcastRepository.searchLibrary(keyword);
        List<Movie> movies = movieRepository.searchLibrary(keyword);
        List<Show> shows = showRepository.searchLibrary(keyword);
        List<Episode> episodes = episodeRepository.searchLibrary(keyword);

        artists.forEach(artist -> {
            MediaDTO artistDTO = MediaDTO.builder().id(artist.getId())
                    .type("artist").title(artist.getName()).image(artist.getImage()).build();
            searchResults.add(artistDTO);
        });

        songs.forEach(song -> {
            MediaDTO songDTO = MediaDTO.builder().id(song.getId())
                    .type("song").title(song.getTitle()).image(song.getAlbum().getAlbumCover()).build();
            searchResults.add(songDTO);
        });

        albums.forEach(album -> {
            MediaDTO albumDTO = MediaDTO.builder().id(album.getId())
                    .type("album").title(album.getTitle()).image(album.getAlbumCover()).build();
            searchResults.add(albumDTO);
        });

        podcasts.forEach(podcast -> {
            MediaDTO podcastDTO = MediaDTO.builder().id(podcast.getId())
                    .type("podcast").title(podcast.getTitle()).image(podcast.getThumbnail()).build();
            searchResults.add(podcastDTO);
        });

        movies.forEach(movie -> {
            MediaDTO movieDTO = MediaDTO.builder().id(movie.getId())
                    .type("movie").title(movie.getTitle()).image(movie.getPoster()).build();
            searchResults.add(movieDTO);
        });

        shows.forEach(show -> {
            MediaDTO showDTO = MediaDTO.builder().id(show.getId())
                    .type("show").title(show.getTitle()).image(show.getPoster()).build();
            searchResults.add(showDTO);
        });

        episodes.forEach(episode -> {
            MediaDTO episodeDTO = MediaDTO.builder().id(episode.getId())
                    .type("episode").title(episode.getTitle()).image(episode.getShowName().getPoster()).build();
            searchResults.add(episodeDTO);
        });

        return searchResults;
    }
}

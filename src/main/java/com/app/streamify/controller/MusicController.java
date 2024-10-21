package com.app.streamify.controller;

import com.app.streamify.dto.AlbumDTO;
import com.app.streamify.dto.SongDTO;
import com.app.streamify.model.Album;
import com.app.streamify.model.Artist;
import com.app.streamify.model.Song;
import com.app.streamify.service.MusicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/music")
public class MusicController {
    private final MusicService musicService;


    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

//    @GetMapping("/genres")
//    public Map<String, List<Album>> getAlbums(){
//        return musicService.getGenres();
//    }

    @GetMapping("/album/{albumId}")
    public AlbumDTO getAlbum(@PathVariable Long albumId){
        return musicService.getAlbum(albumId);
    }

    @GetMapping("/track/{trackId}")
    public SongDTO getSong(@PathVariable Long trackId){
        return musicService.getSong(trackId);
    }

    @GetMapping("/albums")
    public List<Album> getAlbums(){
        return musicService.getAlbums();
    }

    @GetMapping("/genre/{genre}/albums")
    public List<AlbumDTO> getGenreAlbum(@PathVariable String genre){
        return musicService.getGenreAlbums(genre);
    }

    @GetMapping("/new-releases")
    public List<AlbumDTO> getNewReleases(){
        return musicService.getNewReleases();
    }

    @GetMapping("/artists")
    public List<Artist> getArtists(){
        return musicService.getArtists();
    }

    @GetMapping("/popular-albums")
    public List<AlbumDTO> getPopularAlbums(){
        return musicService.getPopularAlbums();
    }

    @GetMapping("/popular-genres")
    public List<String> getPopularGenres(){
        return musicService.getPopularGenres();
    }

    @GetMapping("/artist/{artistId}/albums")
    public List<AlbumDTO> getPopularGenres(@PathVariable Long artistId){
        return musicService.getArtistAlbums(artistId);
    }



}

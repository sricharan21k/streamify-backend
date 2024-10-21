package com.app.streamify.service;

import com.app.streamify.dto.AlbumDTO;
import com.app.streamify.dto.SongDTO;
import com.app.streamify.model.Album;
import com.app.streamify.model.Artist;
import com.app.streamify.model.Song;
import com.app.streamify.repository.AlbumRepository;
import com.app.streamify.repository.ArtistRepository;
import com.app.streamify.repository.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MusicService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;

    public MusicService(AlbumRepository albumRepository, ArtistRepository artistRepository, SongRepository songRepository, ModelMapper modelMapper) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
    }

    public AlbumDTO getAlbum(Long albumId){
        Album album = albumRepository.findById(albumId).orElseThrow();;
        return albumMapper(album);
    }

    public SongDTO getSong(Long songId){
         Song song = songRepository.findById(songId).orElseThrow();
        return modelMapper.map(song, SongDTO.class);
    }

    public List<Album> getAlbums(){
        return albumRepository.findAll();
    }

    public List<AlbumDTO> getArtistAlbums(Long artistId){
        List<Album> albums = albumRepository.findByArtistId(artistId);
        List<AlbumDTO> albumDTOList = new ArrayList<>();
        albums.forEach(album -> {
            AlbumDTO albumDTO = albumMapper(album);
            albumDTOList.add(albumDTO);
        });

        return albumDTOList;
    }

    public List<AlbumDTO> getGenreAlbums(String genre){
        List<Album> albums = albumRepository.findByGenre(genre);
        List<AlbumDTO> albumDTOList = new ArrayList<>();
        albums.forEach(album -> {
            AlbumDTO albumDTO = albumMapper(album);
            albumDTOList.add(albumDTO);
        });

        return albumDTOList;
    }

    public List<Artist> getArtists(){
         return artistRepository.findAll();
    }

    public List<String> getGenres(){
        return albumRepository.findAll().stream().map(Album::getGenre).sorted().distinct().toList();
    }

    public List<AlbumDTO> getNewReleases(){
        List<Album> albums = albumRepository.findAll().stream().sorted(Comparator.comparing(Album::getYear).reversed()).limit(10).toList();
        List<AlbumDTO> albumDTOList = new ArrayList<>();
        albums.forEach(album -> {
            AlbumDTO albumDTO = albumMapper(album);
            albumDTOList.add(albumDTO);
        });
        return albumDTOList;
    }

    public List<AlbumDTO> getPopularAlbums(){
        List<Album> albums = albumRepository.findAll();
        List<AlbumDTO> albumDTOList = new ArrayList<>();
        albums.forEach(album -> {
            AlbumDTO albumDTO = albumMapper(album);
               albumDTOList.add(albumDTO);
                });
                return albumDTOList;

        //TODO: find popular albums based on listens and likes
    }

    public List<String> getPopularGenres(){
       return albumRepository.findAll().stream().map(Album::getGenre)
               .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
               .entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).toList().reversed();
    }

    public Map<String, List<Album>> getGenresAndAlbums(){
         return albumRepository.findAll().stream().sorted(Comparator.comparing(Album::getGenre))
                 .collect(Collectors.groupingBy(Album::getGenre));
    }

    public Map<String, List<Album>> getArtistsAndAlbums(){
        return albumRepository.findAll().stream().sorted(Comparator.comparing(Album::getGenre))
                .collect(Collectors.groupingBy(Album::getGenre));
    }

    private AlbumDTO albumMapper(Album album){
        List<Long> trackList = album.getTrackList().stream().map(Song::getId).toList();
        return AlbumDTO.builder().id(album.getId()).title(album.getTitle())
                .albumCover(album.getAlbumCover()).genre(album.getGenre()).year(album.getYear())
                .isSingle(album.isSingle()).artistName(album.getArtist().getName())
                .artistImage(album.getArtist().getImage()).tracks(trackList).build();
    }


}

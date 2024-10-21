package com.app.streamify.service;

import com.app.streamify.dto.MediaDTO;
import com.app.streamify.dto.NewUser;
import com.app.streamify.dto.UpdatePassword;
import com.app.streamify.dto.UserProfileData;
import com.app.streamify.enumeration.UserListType;
import com.app.streamify.model.*;
import com.app.streamify.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserService {
    @Value("${app.image.path}")
    private String imagePath;

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final PodcastRepository podcastRepository;
    private final ArtistRepository artistRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AlbumRepository albumRepository,
                       SongRepository songRepository,
                       MovieRepository movieRepository,
                       ShowRepository showRepository,
                       PodcastRepository podcastRepository, ArtistRepository artistRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.podcastRepository = podcastRepository;
        this.artistRepository = artistRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileData getUserProfile(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return UserProfileData.builder().username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .image(user.getProfileImage()).build();
    }

    public boolean updatePassword(UpdatePassword data) {
        User existingUser = userRepository.findByEmail(data.getEmail()).orElseThrow();
        existingUser.setPassword(passwordEncoder.encode(data.getPassword()));
        try {
            userRepository.save(existingUser);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean updateProfile(MultipartFile image, String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        try {
            if (!image.isEmpty()) {
                try {
                    String originalFilename = image.getOriginalFilename();
//                    String uniqueFilename = generateUniqueFilename(originalFilename);
                    Path profileDir = Paths.get(imagePath, "user-" + username);
                    assert originalFilename != null;
                    Path filePath = profileDir.resolve(originalFilename);

                    Files.createDirectories(profileDir);
                    Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    user.setProfileImage(originalFilename);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save image", e);
                }
            }
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Resource getImage(String username) throws MalformedURLException {
        User user = userRepository.findByUsername(username).orElseThrow();
        Path filePath = Paths.get(imagePath, "user-" + username + "/" + user.getProfileImage());
        System.out.println(filePath.getFileName());
        return new UrlResource(filePath.toUri());
    }

    public boolean deleteImage(String username) {
        try {
            User user = userRepository.findByUsername(username).orElseThrow();
            Path filePath = Paths.get(imagePath, "user-" + username).resolve(user.getProfileImage());
            user.setProfileImage(null);
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getLastPlayed(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getLastPlayed();
    }

    public void updateLastPlayed(String username, String type) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setLastPlayed(type);
        userRepository.save(user);

    }

    public List<MediaDTO> getUserlist(String username, UserListType listType) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<String> userList = switch (listType) {
            case HISTORY -> user.getWatchHistory();
            case WATCHLIST -> user.getWatchlist();
            case RECENT -> user.getRecentPlays();
            case QUEUE -> user.getPlayQueue();
            case SEARCH -> user.getSearchHistory();
        };
        return getMediaData(userList);
    }

    public List<MediaDTO> addItemToUserLibrary(String username, UserListType listType, String item) {
        User user = userRepository.findByUsername(username).orElseThrow();
        switch (listType) {
            case HISTORY -> addUniqueItem(user, user.getWatchHistory(), item);
            case WATCHLIST -> addUniqueItem(user, user.getWatchlist(), item);
            case RECENT -> addUniqueItem(user, user.getRecentPlays(), item);
            case QUEUE -> addUniqueItem(user, user.getPlayQueue(), item);
            case SEARCH -> addUniqueItem(user, user.getSearchHistory(), item);
        }
        User updatedUser = userRepository.save(user);
        List<String> updatedList = switch (listType) {
            case HISTORY -> updatedUser.getWatchHistory();
            case WATCHLIST -> updatedUser.getWatchlist().reversed();
            case RECENT -> updatedUser.getRecentPlays();
            case QUEUE -> updatedUser.getPlayQueue();
            case SEARCH -> updatedUser.getSearchHistory();
        };
        return getMediaData(updatedList);
    }

    public List<MediaDTO> deleteItemFromLibrary(String username, UserListType listType, String item) {
        User user = userRepository.findByUsername(username).orElseThrow();
        switch (listType) {
            case HISTORY -> user.getWatchHistory().remove(item);
            case WATCHLIST -> user.getWatchlist().remove(item);
            case RECENT -> user.getRecentPlays().remove(item);
            case QUEUE -> user.getPlayQueue().remove(item);
            case SEARCH -> user.getSearchHistory().remove(item);
        }
        User updatedUser = userRepository.save(user);
        List<String> updatedList = switch (listType) {
            case HISTORY -> updatedUser.getWatchHistory().stream().sorted(Comparator.reverseOrder()).toList();
            case WATCHLIST -> updatedUser.getWatchlist();
            case RECENT -> updatedUser.getRecentPlays().stream().sorted(Comparator.reverseOrder()).toList();
            case QUEUE -> updatedUser.getPlayQueue();
            case SEARCH -> updatedUser.getSearchHistory().stream().sorted(Comparator.reverseOrder()).toList();
        };
        if (listType == UserListType.HISTORY || listType == UserListType.RECENT) {
            if (updatedList.isEmpty()) {
                user.setLastPlayed(null);
                userRepository.save(user);
            }
        }
        return getMediaData(updatedList);
    }

    private List<MediaDTO> getMediaData(Collection<String> userList) {
        List<MediaDTO> mediaDTOList = new ArrayList<>();
        userList.forEach(item -> {
            String[] mediaData = item.split("-");
            Long id = Long.valueOf(mediaData[0]);
            String mediaType = mediaData[1];
            String title = "";
            String image = "";
            switch (mediaType) {
                case "movie" -> {
                    Movie movie = movieRepository.findById(id).orElseThrow();
                    title = movie.getTitle();
                    image = movie.getPoster();
                }
                case "show" -> {
                    Show show = showRepository.findById(id).orElseThrow();
                    title = show.getTitle();
                    image = show.getPoster();
                }
                case "album" -> {
                    Album album = albumRepository.findById(id).orElseThrow();
                    title = album.getTitle();
                    image = album.getAlbumCover();
                }
                case "song" -> {
                    Song song = songRepository.findById(id).orElseThrow();
                    title = song.getTitle();
                    image = song.getAlbum().getAlbumCover();
                }
                case "podcast" -> {
                    Podcast podcast = podcastRepository.findById(id).orElseThrow();
                    title = podcast.getTitle();
                    image = podcast.getThumbnail();
                }
                case "artist" -> {
                    Artist artist = artistRepository.findById(id).orElseThrow();
                    title = artist.getName();
                    image = artist.getImage();
                }
            }
            MediaDTO media = MediaDTO.builder().id(id).type(mediaType).title(title).image(image).build();
            mediaDTOList.add(media);
        });
        return mediaDTOList;
    }

    private void addUniqueItem(User user, List<String> list, String item) {
        list.remove(item);
        userRepository.save(user);
        list.add(item);
    }

    public String addUser(NewUser userData) {
        if (userRepository.findByUsername(userData.getUsername()).isPresent())
            throw new RuntimeException("User already exists");
        User newUser = User.builder().username(userData.getUsername())
                .password(passwordEncoder.encode(userData.getPassword()))
                .firstname(userData.getFirstname())
                .lastname(userData.getLastname())
                .email(userData.getEmail()).build();

        return userRepository.save(newUser).getUsername();
    }

    public boolean checkUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

}

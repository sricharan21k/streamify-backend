package com.app.streamify.controller;

import com.app.streamify.dto.*;
import com.app.streamify.enumeration.UserListType;
import com.app.streamify.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // User profile
    @GetMapping("/user-profile/{username}")
    public UserProfileData getUserData(@PathVariable String username){
        return userService.getUserProfile(username);
    }

    @PatchMapping("/password")
    public boolean updatePassword(@RequestBody UpdatePassword data){
        return userService.updatePassword(data);
    }

    //    Profile image


    @GetMapping("/{username}/profile-image")
    public Resource getImage(@PathVariable String username) {
        try {
            Resource image = userService.getImage(username);
            if (image.exists() || image.isReadable()) {
                return image;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{username}/profile-image")
    public boolean updateProfile(@RequestParam("profileImage") MultipartFile image, @PathVariable String username) {
        return userService.updateProfile(image, username);
    }

    @DeleteMapping("/{username}/profile-image")
    public boolean deleteImage(@PathVariable String username){
        return userService.deleteImage(username);
    }


    // User login

    @PostMapping("/register")
    public StringDTO newUser(@RequestBody NewUser userData){
        String x = userService.addUser(userData);
        return StringDTO.builder().data(x).build();
    }

    @GetMapping("/check/{username}")
    public boolean checkUserExists(@PathVariable String username){
        boolean x =  userService.checkUserExists(username);
        System.out.println("x: "+x);
        return x;
    }


    // User app data

    @GetMapping("/{username}/last-played")
    public StringDTO getLastPlayed(@PathVariable String username){
        String lastPlayed = userService.getLastPlayed(username);
        return StringDTO.builder().data(lastPlayed).build();
    }

    @GetMapping("/{username}/watch-history")
    public List<MediaDTO> getUserWatchHistory(@PathVariable String username){
        return userService.getUserlist(username, UserListType.HISTORY);
    }

    @GetMapping("/{username}/watchlist")
    public List<MediaDTO> getUserWatchlist(@PathVariable String username){
        return userService.getUserlist(username, UserListType.WATCHLIST);
    }

    @GetMapping("/{username}/recent-plays")
    public List<MediaDTO> getUserRecentPlays(@PathVariable String username){
        return userService.getUserlist(username, UserListType.RECENT);
    }

    @GetMapping("/{username}/play-queue")
    public List<MediaDTO> getUserPlayQueue(@PathVariable String username){
        return userService.getUserlist(username, UserListType.QUEUE);
    }

    @GetMapping("/{username}/search-history")
    public List<MediaDTO> getUserSearchHistory(@PathVariable String username){
        return userService.getUserlist(username, UserListType.SEARCH);
    }

    @PutMapping("/{username}/watch-history")
    public List<MediaDTO> addItemToUserWatchHistory(@PathVariable String username, @RequestBody String item){
         return userService.addItemToUserLibrary(username, UserListType.HISTORY, item);
    }

    @PutMapping("/{username}/watchlist")
    public List<MediaDTO> addItemToUserWatchlist(@PathVariable String username, @RequestBody String item){
        return userService.addItemToUserLibrary(username, UserListType.WATCHLIST, item);
    }

    @PutMapping("/{username}/recent-plays")
    public List<MediaDTO> addItemToUserRecentPlays(@PathVariable String username, @RequestBody String item){
        return userService.addItemToUserLibrary(username, UserListType.RECENT, item);
    }

    @PutMapping("/{username}/play-queue")
    public List<MediaDTO> addItemToUserPlayQueue(@PathVariable String username, @RequestBody String item){
        return userService.addItemToUserLibrary(username, UserListType.QUEUE, item);
    }

    @PutMapping("/{username}/search-history")
    public List<MediaDTO> addItemToUserSearchHistory(@PathVariable String username, @RequestBody String item){
        return userService.addItemToUserLibrary(username, UserListType.SEARCH, item);
    }

    @PostMapping("/{username}/last-played")
    public void getLastPlayed(@PathVariable String username, @RequestBody StringDTO stringDTO){
        userService.updateLastPlayed(username, stringDTO.getData());
    }

    @DeleteMapping("/{username}/watch-history/{item}")
    public List<MediaDTO> deleteItemFromUserWatchHistory(@PathVariable String username, @PathVariable String item){
        return userService.deleteItemFromLibrary(username, UserListType.HISTORY, item);
    }

    @DeleteMapping("/{username}/watchlist/{item}")
    public List<MediaDTO> deleteItemFromUserWatchlist(@PathVariable String username, @PathVariable String item){
        return userService.deleteItemFromLibrary(username, UserListType.WATCHLIST, item);
    }

    @DeleteMapping("/{username}/recent-plays/{item}")
    public List<MediaDTO> deleteItemFromUserRecentPlays(@PathVariable String username, @PathVariable String item){
        return userService.deleteItemFromLibrary(username, UserListType.RECENT, item);
    }

    @DeleteMapping("/{username}/play-queue/{item}")
    public List<MediaDTO> deleteItemFromUserPlayQueue(@PathVariable String username, @PathVariable String item){
        return userService.deleteItemFromLibrary(username, UserListType.QUEUE, item);
    }

    @DeleteMapping("/{username}/search-history/{item}")
    public List<MediaDTO> deleteItemFromUserSearchHistory(@PathVariable String username, @PathVariable String item){
        return userService.deleteItemFromLibrary(username, UserListType.SEARCH, item);
    }


}

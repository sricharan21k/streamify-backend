package com.app.streamify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "streamify_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String profileImage;
    private String lastPlayed;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "history_item")
    @OrderColumn
    private List<String> watchHistory = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "watchlist_item")
    @OrderColumn
    private List<String> watchlist = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "recent_item")
    @OrderColumn
    private List<String> recentPlays = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "queued_item")
    @OrderColumn
    private List<String> playQueue = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "search_item")
    @OrderColumn
    private List<String> searchHistory = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

}

package com.app.streamify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileData {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String image;
}
